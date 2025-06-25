package com.kristalbal.assetmgmt.service;

import com.kristalbal.assetmgmt.model.Purchase;
import java.util.List;

import org.springframework.security.core.Authentication;

public interface PurchaseService {
    Purchase createPurchase(Purchase purchase);
    List<Purchase> getAllPurchases();
    Purchase getPurchaseById(Long id);
    void deletePurchase(Long id);
	List<Purchase> getPurchasesForUser(Authentication auth);
}

