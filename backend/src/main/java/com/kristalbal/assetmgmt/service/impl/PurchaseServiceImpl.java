package com.kristalbal.assetmgmt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.kristalbal.assetmgmt.model.Purchase;
import com.kristalbal.assetmgmt.model.User;
import com.kristalbal.assetmgmt.repository.PurchaseRepository;
import com.kristalbal.assetmgmt.repository.UserRepository;
import com.kristalbal.assetmgmt.service.PurchaseService;

@Service
public class PurchaseServiceImpl implements PurchaseService{
	
	@Autowired
	private PurchaseRepository purchaseRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Purchase createPurchase(Purchase purchase) {
		return purchaseRepository.save(purchase);
	}

	@Override
	public List<Purchase> getAllPurchases() {
		return purchaseRepository.findAll();
	}

	@Override
	public Purchase getPurchaseById(Long id) {
		return purchaseRepository.findById(id).orElseThrow(() -> new RuntimeException("Purchase not found"));
	}

	@Override
	public void deletePurchase(Long id) {
		purchaseRepository.deleteById(id);
		
	}
	
	
	@Override
	public List<Purchase> getPurchasesForUser(Authentication auth) {
	    String username = auth.getName();
	    User user = userRepository.findByUsername(username)
	                  .orElseThrow(() -> new RuntimeException("User not found"));

	    if (user.getRole().equals("LOGISTICS")) {
	        Long baseId = user.getBase().getId();
	        return purchaseRepository.findByBase_Id(baseId); // Use your base-aware method
	    }

	    if (user.getRole().equals("ADMIN")) {
	        return purchaseRepository.findAll();
	    }

	    throw new AccessDeniedException("You are not authorized to view purchases.");
	}


}
