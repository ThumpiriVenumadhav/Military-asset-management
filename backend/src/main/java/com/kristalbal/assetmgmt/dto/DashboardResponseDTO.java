package com.kristalbal.assetmgmt.dto;

public class DashboardResponseDTO {
    private long totalUsers;
    private long totalAssets;
    private long totalAssignments;
    private long totalPurchases;
    private long totalTransfers;

    // Constructors
    public DashboardResponseDTO() {}

	public DashboardResponseDTO(long totalUsers, long totalAssets, long totalAssignments, long totalPurchases,
			long totalTransfers) {
		
		this.totalUsers = totalUsers;
		this.totalAssets = totalAssets;
		this.totalAssignments = totalAssignments;
		this.totalPurchases = totalPurchases;
		this.totalTransfers = totalTransfers;
	}

	public long getTotalUsers() {
		return totalUsers;
	}

	public void setTotalUsers(long totalUsers) {
		this.totalUsers = totalUsers;
	}

	public long getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(long totalAssets) {
		this.totalAssets = totalAssets;
	}

	public long getTotalAssignments() {
		return totalAssignments;
	}

	public void setTotalAssignments(long totalAssignments) {
		this.totalAssignments = totalAssignments;
	}

	public long getTotalPurchases() {
		return totalPurchases;
	}

	public void setTotalPurchases(long totalPurchases) {
		this.totalPurchases = totalPurchases;
	}

	public long getTotalTransfers() {
		return totalTransfers;
	}

	public void setTotalTransfers(long totalTransfers) {
		this.totalTransfers = totalTransfers;
	}

    
}
