package com.kristalbal.assetmgmt.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

@Entity

public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String assignedTo;
    private LocalDate assignedOn;
    private LocalDate returnedOn;
    private int Quantity;
    @ManyToOne
    @JoinColumn(name = "asset_id")
    private Asset asset;
    
    public Assignment() {
		// TODO Auto-generated constructor stub
	}

	public Assignment(Long id, String assignedTo, LocalDate assignedOn, LocalDate returnedOn, Asset asset) {
		
		this.id = id;
		this.assignedTo = assignedTo;
		this.assignedOn = assignedOn;
		this.returnedOn = returnedOn;
		this.asset = asset;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAssignedTo() {
		return assignedTo;
	}

	public void setAssignedTo(String assignedTo) {
		this.assignedTo = assignedTo;
	}

	public LocalDate getAssignedOn() {
		return assignedOn;
	}

	public void setAssignedOn(LocalDate assignedOn) {
		this.assignedOn = assignedOn;
	}

	public LocalDate getReturnedOn() {
		return returnedOn;
	}

	public void setReturnedOn(LocalDate returnedOn) {
		this.returnedOn = returnedOn;
	}
	
	

	public int getQuantity() {
		return Quantity;
	}

	public void setQuantity(int quantity) {
		Quantity = quantity;
	}

	public Asset getAsset() {
		return asset;
	}

	public void setAsset(Asset asset) {
		this.asset = asset;
	}
	
	
}
