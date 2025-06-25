package com.kristalbal.assetmgmt.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kristalbal.assetmgmt.model.Base;
import com.kristalbal.assetmgmt.repository.BaseRepository;
import com.kristalbal.assetmgmt.service.BaseService;

@Service
public class BaseServiceImpl implements BaseService {
	
	@Autowired
	private BaseRepository baseRepository;

	@Override
	public Base createBase(Base base) {
		
		
		return baseRepository.save(base);
	}

	@Override
	public List<Base> getAllBases() {
		
		return baseRepository.findAll();
	}

	@Override
	public Base getBaseById(Long id) {
		
		return baseRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("Base not found with Id: "+id));
	}

	@Override
	public Base updateBase(Long id, Base baseDetails) {
		Base base = getBaseById(id);
		base.setName(baseDetails.getName());
		base.setLocation(baseDetails.getLocation());
		return baseRepository.save(base);
	}

	@Override
	public void deleteBase(Long id) {
		baseRepository.deleteById(id);

	}

}
