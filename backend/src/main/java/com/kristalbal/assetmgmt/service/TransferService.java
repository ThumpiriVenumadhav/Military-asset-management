package com.kristalbal.assetmgmt.service;

import com.kristalbal.assetmgmt.dto.TransferRequestDTO;
import com.kristalbal.assetmgmt.model.Transfer;
import java.util.List;

import org.springframework.security.core.Authentication;

public interface TransferService {
    Transfer createTransfer(Transfer transfer);
    List<Transfer> getAllTransfers();
    Transfer getTransferById(Long id);
    void deleteTransfer(Long id);
	Transfer createTransferForUser(Transfer transfer, Authentication auth);
	List<Transfer> getTransfersForUser(Authentication auth);
	Transfer createTransferFromDto(TransferRequestDTO request, Authentication auth);
}
