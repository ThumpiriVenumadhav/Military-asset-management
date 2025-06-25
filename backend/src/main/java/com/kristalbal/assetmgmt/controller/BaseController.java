package com.kristalbal.assetmgmt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kristalbal.assetmgmt.model.Base;
import com.kristalbal.assetmgmt.service.BaseService;

@RestController
@RequestMapping("/api/bases")
public class BaseController {
	
	@Autowired
	private BaseService baseService;
	
	@PostMapping
	public Base createBase(@RequestBody Base base) {
		return baseService.createBase(base);
	}
	
	@GetMapping
	public List<Base> getAllBases(){
		return baseService.getAllBases();
	}
	
	
	@GetMapping("/{id}")
	public Base getBaseById(@PathVariable Long id) {
		return baseService.getBaseById(id);
	}
	
	@PutMapping("/{id}")
	public Base updateBase(@PathVariable Long id,@RequestBody Base BaseDetails) {
		return baseService.updateBase(id, BaseDetails);
	}
	
	@DeleteMapping("/{id}")
	public String deleteBase(@PathVariable Long id) {
		baseService.deleteBase(id);
		return "Base deleted successfully";
	}
	
}
