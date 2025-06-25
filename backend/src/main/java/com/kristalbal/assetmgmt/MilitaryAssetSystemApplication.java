package com.kristalbal.assetmgmt;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.kristalbal.assetmgmt.model.User;
import com.kristalbal.assetmgmt.repository.UserRepository;

@SpringBootApplication
public class MilitaryAssetSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(MilitaryAssetSystemApplication.class, args);
	}
	
	
	
	


}
