package com.kristalbal.assetmgmt.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kristalbal.assetmgmt.model.Assignment;

public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
	List<Assignment> findByAsset_Base_Id(Long baseId);

	long countByAsset_Base_Id(Long baseId);

	@Query("SELECT COALESCE(SUM(p.quantity), 0) FROM Purchase p WHERE p.asset.id = :assetId AND p.base.id = :baseId")
	int sumByAssetAndBase(@Param("assetId") Long assetId, @Param("baseId") Long baseId);



}
