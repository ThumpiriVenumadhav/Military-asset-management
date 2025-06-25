package com.kristalbal.assetmgmt.controller;

import com.kristalbal.assetmgmt.dto.DashboardResponseDTO;
import com.kristalbal.assetmgmt.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;


@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {

    @Autowired
    private DashboardService dashboardService;

    @GetMapping
    public DashboardResponseDTO getDashboard(Authentication auth) {
        return dashboardService.getDashboardData(auth);
    }
}
