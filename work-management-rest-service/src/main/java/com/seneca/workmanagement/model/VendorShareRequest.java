package com.seneca.workmanagement.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class VendorShareRequest {
	@ApiModelProperty(notes = "Total no of tasks that need to be shared for vendors.", name = "total_tasks", required = true)
	@JsonProperty("total_tasks")
	private int totalTasks;

	@ApiModelProperty(notes = "List of vendors with name and percentage details.", name = "vendors", required = true)
	private List<Vendor> vendors;

	public int getTotalTasks() {
		return totalTasks;
	}

	public void setTotalTasks(int totalTasks) {
		this.totalTasks = totalTasks;
	}

	public List<Vendor> getVendors() {
		return vendors;
	}

	public void setVendors(List<Vendor> vendorDetailsList) {
		this.vendors = vendorDetailsList;
	}
}