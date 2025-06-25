package com.kristalbal.assetmgmt.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity

public class Expenditure {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate date;
    private String reason;

    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;

    @ManyToOne
    @JoinColumn(name = "base_id")
    private Base base;
    
    public Expenditure() {
		// TODO Auto-generated constructor stub
	}

	public Expenditure(Long id, LocalDate date, String reason, Asset asset, Base base) {
		super();
		this.id = id;
		this.date = date;
		this.reason = reason;
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

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
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
