package com.kristalbal.assetmgmt.service;

import java.util.List;

import com.kristalbal.assetmgmt.model.Base;

public interface BaseService {
	Base createBase(Base base);
    List<Base> getAllBases();
    Base getBaseById(Long id);
    Base updateBase(Long id, Base baseDetails);
    void deleteBase(Long id);
}
