package com.kristalbal.assetmgmt.service.impl;

import com.kristalbal.assetmgmt.dto.DashboardResponseDTO;
import com.kristalbal.assetmgmt.model.User;
import com.kristalbal.assetmgmt.repository.*;
import com.kristalbal.assetmgmt.service.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class DashboardServiceImpl implements DashboardService {

    @Autowired private UserRepository userRepository;
    @Autowired private AssetRepository assetRepository;
    @Autowired private AssignmentRepository assignmentRepository;
    @Autowired private PurchaseRepository purchaseRepository;
    @Autowired private TransferRepository transferRepository;

    @Override
    public DashboardResponseDTO getDashboardData(Authentication auth) {
        User user = userRepository.findByUsername(auth.getName()).orElseThrow();

        DashboardResponseDTO dto = new DashboardResponseDTO();

        if (user.getRole().equals("ADMIN")) {
            dto.setTotalUsers(userRepository.count());
            dto.setTotalAssets(assetRepository.count());
            dto.setTotalAssignments(assignmentRepository.count());
            dto.setTotalPurchases(purchaseRepository.count());
            dto.setTotalTransfers(transferRepository.count());
        } else {
            Long baseId = user.getBase().getId();

            dto.setTotalAssets(assetRepository.countByBase_Id(baseId));
            dto.setTotalAssignments(assignmentRepository.countByAsset_Base_Id(baseId));
            dto.setTotalTransfers(transferRepository.countByFromBase_IdOrToBase_Id(baseId, baseId));

            if (user.getRole().equals("LOGISTICS")) {
                dto.setTotalPurchases(purchaseRepository.countByBase_Id(baseId));
            }
        }

        return dto;
    }
}
