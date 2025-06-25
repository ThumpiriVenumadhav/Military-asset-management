package com.kristalbal.assetmgmt.dto;

import java.time.LocalDate;

public class TransferRequestDTO {
    private LocalDate transferDate;
    private Long assetId;
    private Long fromBaseId;
    private Long toBaseId;
    private int quantity; 

    public LocalDate getTransferDate() {
        return transferDate;
    }

    public void setTransferDate(LocalDate transferDate) {
        this.transferDate = transferDate;
    }

    public Long getAssetId() {
        return assetId;
    }

    public void setAssetId(Long assetId) {
        this.assetId = assetId;
    }

    public Long getToBaseId() {
        return toBaseId;
    }

    public void setToBaseId(Long toBaseId) {
        this.toBaseId = toBaseId;
    }

	public Long getFromBaseId() {
		return fromBaseId;
	}

	public void setFromBaseId(Long fromBaseId) {
		this.fromBaseId = fromBaseId;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
    
    
}
