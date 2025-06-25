package com.kristalbal.assetmgmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kristalbal.assetmgmt.model.Expenditure;

public interface ExpenditureRepository extends JpaRepository<Expenditure, Long> {

}
