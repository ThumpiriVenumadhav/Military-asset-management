package com.kristalbal.assetmgmt.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity

public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    
    
    private int quantity;

    
	@ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "base_id")
    private Base base;
    
    public Purchase() {
		// TODO Auto-generated constructor stub
	}

	public Purchase(Long id, LocalDate date, Asset asset, Base base) {
		super();
		this.id = id;
		this.date = date;
		this.asset = asset;
		this.base = base;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}
	
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public Base getBase() {
		return base;
	}

	public void setBase(Base base) {
		this.base = base;
	}
    
    
}
