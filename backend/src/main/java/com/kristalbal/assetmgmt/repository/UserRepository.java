package com.kristalbal.assetmgmt.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kristalbal.assetmgmt.model.User;

public interface UserRepository extends JpaRepository<User, Long> {

	
	Optional<User> findByUsername(String username);
}
