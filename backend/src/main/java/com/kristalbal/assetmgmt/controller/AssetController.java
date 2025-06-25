package com.kristalbal.assetmgmt.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kristalbal.assetmgmt.model.Asset;
import com.kristalbal.assetmgmt.service.impl.AssetServiceImpl;

@RestController
@RequestMapping("/api/assets")
public class AssetController {
	@Autowired
	private AssetServiceImpl assetServiceImpl;
	
	@PostMapping
	public Asset createAsset(@RequestBody Asset asset) {
		return assetServiceImpl.createAsset(asset);
	}
	
	
	
	
	@PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS', 'COMMANDER')")
	@GetMapping("/inventory")
	public ResponseEntity<Map<String, Integer>> getInventory(
	    @RequestParam Long assetId,
	    @RequestParam Long baseId
	) {
	    int available = assetServiceImpl.getAvailableQuantity(assetId, baseId);
	    return ResponseEntity.ok(Map.of("available", available));
	}
	
	
	

	@PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS', 'COMMANDER')")
	@GetMapping
	public List<Asset> getAllAssets(){
		System.out.println("/api/assets");
		return assetServiceImpl.getAllAssets();
	}
	
	@PreAuthorize("hasAnyRole('ADMIN', 'LOGISTICS', 'COMMANDER')")
	@GetMapping("/{id}")
	public Asset getAssetById(@PathVariable Long id) {
		return assetServiceImpl.getAssetById(id);
	}
	
	@PutMapping("/{id}")
	public Asset updateAsset(@PathVariable Long id,@RequestBody Asset assetDetails) {
		return assetServiceImpl.updateAsset(id, assetDetails);
	}
	
	@DeleteMapping("{id}")
	public String deleteAsset(@PathVariable Long id) {
		assetServiceImpl.deleteAsset(id);
		return "Asset deleted successfully";
	}
	
}
