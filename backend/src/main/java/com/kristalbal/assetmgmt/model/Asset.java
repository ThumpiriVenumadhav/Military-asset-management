package com.kristalbal.assetmgmt.model;

import jakarta.persistence.*;
import lombok.*;

@Entity

public class Asset {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String type; // Weapon, Vehicle, etc.
    private String serialNumber;
    private String status;
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "base_id")
    private Base base;
    
    
    
    public Asset() {
		// TODO Auto-generated constructor stub
	}

	public Asset(Long id, String name, String type, String serialNumber, String status, Base base) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
		this.serialNumber = serialNumber;
		this.status = status;
		this.base = base;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}
    
    
    
}

