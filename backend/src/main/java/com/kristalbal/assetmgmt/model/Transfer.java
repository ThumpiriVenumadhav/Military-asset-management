package com.kristalbal.assetmgmt.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity

public class Transfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "from_base_id")
    private Base fromBase;

    @ManyToOne
    @JoinColumn(name = "to_base_id")
    private Base toBase;

    private LocalDateTime timestamp;
    
    private int quantity;
    
    

	public Transfer() {
		// TODO Auto-generated constructor stub
	}

	public Transfer(Long id, Asset asset, Base fromBase, Base toBase, LocalDateTime timestamp) {
		super();
		this.id = id;
		this.asset = asset;
		this.fromBase = fromBase;
		this.toBase = toBase;
		this.timestamp = timestamp;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}

	public Base getFromBase() {
		return fromBase;
	}

	public void setFromBase(Base fromBase) {
		this.fromBase = fromBase;
	}

	public Base getToBase() {
		return toBase;
	}

	public void setToBase(Base toBase) {
		this.toBase = toBase;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}
	
	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
    
    
}
