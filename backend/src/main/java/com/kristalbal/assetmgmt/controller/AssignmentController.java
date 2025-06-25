package com.kristalbal.assetmgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kristalbal.assetmgmt.model.Assignment;
import com.kristalbal.assetmgmt.repository.UserRepository;
import com.kristalbal.assetmgmt.service.AssignmentService;

@RestController
@RequestMapping("/api/assignments")
public class AssignmentController {

    @Autowired
    private AssignmentService assignmentService;
    
    @Autowired
    private UserRepository userRepository;

    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS', 'COMMANDER')")
    @PostMapping
    public Assignment createAssignment(@RequestBody Assignment assignment) {
        return assignmentService.createAssignment(assignment);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS', 'COMMANDER')")
    @GetMapping
    public List<Assignment> getAllAssignments(Authentication authentication) {
        return assignmentService.getAssignmentsForUser(authentication);
    }


    @GetMapping("/{id}")
    public Assignment getAssignmentById(@PathVariable Long id) {
        return assignmentService.getAssignmentById(id);
    }

    @PutMapping("/{id}/return")
    public Assignment markReturned(@PathVariable Long id) {
        return assignmentService.markReturned(id);
    }

    @DeleteMapping("/{id}")
    public String deleteAssignment(@PathVariable Long id) {
        assignmentService.deleteAssignment(id);
        return "Assignment deleted successfully!";
    }
}
