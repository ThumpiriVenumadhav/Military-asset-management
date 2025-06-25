package com.kristalbal.assetmgmt.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.kristalbal.assetmgmt.model.Assignment;
import com.kristalbal.assetmgmt.model.User;
import com.kristalbal.assetmgmt.repository.AssignmentRepository;
import com.kristalbal.assetmgmt.repository.UserRepository;
import com.kristalbal.assetmgmt.service.AssignmentService;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    @Autowired
    private AssignmentRepository assignmentRepository;
    
    @Autowired
    private UserRepository userRepository;
    

    @Override
    public Assignment createAssignment(Assignment assignment) {
        assignment.setAssignedOn(LocalDate.now());
        return assignmentRepository.save(assignment);
    }

    @Override
    public List<Assignment> getAllAssignments() {
        return assignmentRepository.findAll();
    }

    @Override
    public Assignment getAssignmentById(Long id) {
        return assignmentRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Assignment not found with ID: " + id));
    }

    @Override
    public Assignment markReturned(Long id) {
        Assignment assignment = getAssignmentById(id);
        assignment.setReturnedOn(LocalDate.now());
        return assignmentRepository.save(assignment);
    }

    @Override
    public void deleteAssignment(Long id) {
        assignmentRepository.deleteById(id);
    }
    
    @Override
    public List<Assignment> getAssignmentsForUser(Authentication auth) {
        String username = auth.getName();
        User user = userRepository.findByUsername(username)
                      .orElseThrow(() -> new RuntimeException("User not found"));

        switch (user.getRole()) {
            case "ADMIN":
                return assignmentRepository.findAll();

            case "COMMANDER":
                Long baseId = user.getBase().getId();
                return assignmentRepository.findByAsset_Base_Id(baseId);
            default:
                throw new AccessDeniedException("You are not authorized to view assignments.");
        }
    }

    
}
