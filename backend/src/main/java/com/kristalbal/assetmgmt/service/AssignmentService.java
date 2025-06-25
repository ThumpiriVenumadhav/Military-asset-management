package com.kristalbal.assetmgmt.service;

import java.util.List;

import org.springframework.security.core.Authentication;

import com.kristalbal.assetmgmt.model.Assignment;

public interface AssignmentService {
	Assignment createAssignment(Assignment assignment);
    List<Assignment> getAllAssignments();
    Assignment getAssignmentById(Long id);
    Assignment markReturned(Long id);
    void deleteAssignment(Long id);
    List<Assignment> getAssignmentsForUser(Authentication auth);

}
