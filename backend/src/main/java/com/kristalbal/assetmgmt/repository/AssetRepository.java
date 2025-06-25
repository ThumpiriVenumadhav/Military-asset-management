package com.kristalbal.assetmgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kristalbal.assetmgmt.model.Asset;
import com.kristalbal.assetmgmt.model.Base;

public interface AssetRepository extends JpaRepository<Asset, Long> {

	List<Asset> findByBase(Base base);

	long countByBase_Id(Long baseId);
}
