package com.kristalbal.assetmgmt.service;

import com.kristalbal.assetmgmt.dto.DashboardResponseDTO;
import org.springframework.security.core.Authentication;

public interface DashboardService {
    DashboardResponseDTO getDashboardData(Authentication auth);
}
