package com.kristalbal.assetmgmt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kristalbal.assetmgmt.model.Asset;
import com.kristalbal.assetmgmt.model.Base;
import com.kristalbal.assetmgmt.repository.AssetRepository;
import com.kristalbal.assetmgmt.repository.AssignmentRepository;
import com.kristalbal.assetmgmt.repository.BaseRepository;
import com.kristalbal.assetmgmt.repository.PurchaseRepository;
import com.kristalbal.assetmgmt.repository.TransferRepository;
import com.kristalbal.assetmgmt.service.AssetService;

@Service
public class AssetServiceImpl implements AssetService {
	
	@Autowired
	private AssetRepository assetRepository;
	@Autowired
	private PurchaseRepository purchaseRepository;
	@Autowired
	private AssignmentRepository assignmentRepository;
	@Autowired
	private TransferRepository transferRepository;
	
	@Autowired
	private BaseRepository baseRepository;
	
	@Override
	public Asset createAsset(Asset asset) {
	    Long baseId = asset.getBase().getId();
	    Base managedBase = baseRepository.findById(baseId)
	        .orElseThrow(() -> new RuntimeException("Base not found"));

	    asset.setBase(managedBase);
	    return assetRepository.save(asset);
	}


	@Override
	public List<Asset> getAllAssets() {
		return assetRepository.findAll();
	}

	@Override
	public Asset getAssetById(Long id) {
		return assetRepository.findById(id).orElseThrow(() -> new RuntimeException("Asset not find with id " + id));
	}

	@Override
	public List<Asset> getAssetsByBase(Base base) {
		return assetRepository.findByBase(base);
	}

	@Override
	public Asset updateAsset(Long id, Asset assetDetails) {
		Asset asset = getAssetById(id);
		asset.setBase(assetDetails.getBase());
		asset.setName(assetDetails.getName());
        asset.setType(assetDetails.getType());
        asset.setStatus(assetDetails.getStatus());
        asset.setQuantity(assetDetails.getQuantity());
        asset.setSerialNumber(assetDetails.getSerialNumber());
        return assetRepository.save(asset);
	}

	@Override
	public void deleteAsset(Long id) {
		assetRepository.deleteById(id);
		
	}
	
	public int getAvailableQuantity(Long assetId, Long baseId) {
	    int purchased = purchaseRepository.sumByAssetAndBase(assetId, baseId);
	    int assigned = assignmentRepository.sumByAssetAndBase(assetId, baseId);
	    int transferredOut = transferRepository.sumByAssetFromBase(assetId, baseId);
	    int transferredIn = transferRepository.sumByAssetToBase(assetId, baseId);

	    return purchased - assigned - transferredOut + transferredIn;
	}

	

}
