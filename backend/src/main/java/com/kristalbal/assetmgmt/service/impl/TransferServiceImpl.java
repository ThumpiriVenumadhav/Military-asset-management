package com.kristalbal.assetmgmt.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.kristalbal.assetmgmt.dto.TransferRequestDTO;
import com.kristalbal.assetmgmt.model.Asset;
import com.kristalbal.assetmgmt.model.Base;
import com.kristalbal.assetmgmt.model.Transfer;
import com.kristalbal.assetmgmt.model.User;
import com.kristalbal.assetmgmt.repository.AssetRepository;
import com.kristalbal.assetmgmt.repository.BaseRepository;
import com.kristalbal.assetmgmt.repository.TransferRepository;
import com.kristalbal.assetmgmt.repository.UserRepository;
import com.kristalbal.assetmgmt.service.TransferService;

@Service
public class TransferServiceImpl implements TransferService {

    @Autowired
    private TransferRepository transferRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BaseRepository baseRepository;
    
    @Autowired
    private AssetRepository assetRepository;

    @Override
    public Transfer createTransfer(Transfer transfer) {
        transfer.setTimestamp(LocalDateTime.now());
        return transferRepository.save(transfer);
    }

    @Override
    public List<Transfer> getAllTransfers() {
        return transferRepository.findAll();
    }

    @Override
    public Transfer getTransferById(Long id) {
        return transferRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Transfer not found with ID: " + id));
    }

    @Override
    public void deleteTransfer(Long id) {
        transferRepository.deleteById(id);
    }

	@Override
	public Transfer createTransferForUser(Transfer transfer, Authentication auth) {
		 String username = auth.getName();
		    User user = userRepository.findByUsername(username).orElseThrow();

		    if (!transfer.getFromBase().getId().equals(user.getBase().getId())) {
		        throw new AccessDeniedException("You cannot create transfers from another base.");
		    }

		    return transferRepository.save(transfer);
	}

	@Override
	public List<Transfer> getTransfersForUser(Authentication auth) {
		 String username = auth.getName();
		    User user = userRepository.findByUsername(username)
		                  .orElseThrow(() -> new RuntimeException("User not found"));

		    if (user.getRole().equals("ADMIN")) {
		        return transferRepository.findAll(); // optional, if admins can see all transfers
		    }

		    Long baseId = user.getBase().getId();
		    return transferRepository.findByFromBase_IdOrToBase_Id(baseId, baseId);
	}
	
	public Transfer createTransferFromDto(TransferRequestDTO request, Authentication auth) {
	    Asset asset = assetRepository.findById(request.getAssetId())
	        .orElseThrow();

	    Base fromBase = baseRepository.findById(request.getFromBaseId())
	        .orElseThrow();

	    Base toBase = baseRepository.findById(request.getToBaseId())
	        .orElseThrow();

	    Transfer transfer = new Transfer();
	    transfer.setAsset(asset);
	    transfer.setFromBase(fromBase);
	    transfer.setToBase(toBase);
	    transfer.setQuantity(request.getQuantity());
	    transfer.setTimestamp(LocalDateTime.now());

	    return transferRepository.save(transfer);
	}


}
