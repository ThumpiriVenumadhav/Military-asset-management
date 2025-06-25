package com.kristalbal.assetmgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kristalbal.assetmgmt.model.Transfer;

public interface TransferRepository extends JpaRepository<Transfer, Long> {
	List<Transfer> findByFromBase_IdOrToBase_Id(Long fromBaseId, Long toBaseId);

	long countByFromBase_IdOrToBase_Id(Long baseId, Long baseId2);

	// Total quantity transferred OUT from a base
	@Query("SELECT COALESCE(SUM(t.quantity), 0) FROM Transfer t WHERE t.asset.id = :assetId AND t.fromBase.id = :baseId")
	int sumByAssetFromBase(@Param("assetId") Long assetId, @Param("baseId") Long baseId);

	// Total quantity transferred IN to a base
	@Query("SELECT COALESCE(SUM(t.quantity), 0) FROM Transfer t WHERE t.asset.id = :assetId AND t.toBase.id = :baseId")
	int sumByAssetToBase(@Param("assetId") Long assetId, @Param("baseId") Long baseId);

	
	
	
	
}
