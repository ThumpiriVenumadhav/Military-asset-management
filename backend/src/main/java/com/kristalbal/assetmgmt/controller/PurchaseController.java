package com.kristalbal.assetmgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kristalbal.assetmgmt.model.Purchase;
import com.kristalbal.assetmgmt.service.PurchaseService;

@RestController
@RequestMapping("/api/purchases")
public class PurchaseController {
	
	public PurchaseController() {
		System.out.println("Entering  into purchaseController");
	}
	
	@Autowired
	private PurchaseService purchaseService;
	
	@PreAuthorize("hasRole('ADMIN') or hasRole('LOGISTICS')")
	@PostMapping
    public Purchase createPurchase(@RequestBody Purchase purchase,Authentication authentication) {
		System.out.println("User: " + authentication.getName());
	    System.out.println("Roles: " + authentication.getAuthorities());
        return purchaseService.createPurchase(purchase);
    }

//    @GetMapping
//    public List<Purchase> getAllPurchases() {
//        return purchaseService.getAllPurchases();
//    }
    
    @GetMapping
    public List<Purchase> getPurchasesForUser(Authentication authentication) {
        return purchaseService.getPurchasesForUser(authentication);
    }

    

    @GetMapping("/{id}")
    public Purchase getPurchaseById(@PathVariable Long id) {
        return purchaseService.getPurchaseById(id);
    }

    @DeleteMapping("/{id}")
    public String deletePurchase(@PathVariable Long id) {
        purchaseService.deletePurchase(id);
        return "Purchase deleted successfully!";
    }
}
