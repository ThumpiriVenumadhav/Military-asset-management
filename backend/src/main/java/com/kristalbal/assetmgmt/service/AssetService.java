package com.kristalbal.assetmgmt.service;

import java.util.List;

import com.kristalbal.assetmgmt.model.Asset;
import com.kristalbal.assetmgmt.model.Base;

public interface AssetService {
	Asset createAsset(Asset asset);
    List<Asset> getAllAssets();
    Asset getAssetById(Long id);
    List<Asset> getAssetsByBase(Base base);
    Asset updateAsset(Long id, Asset assetDetails);
    void deleteAsset(Long id);
}
