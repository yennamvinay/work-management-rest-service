package com.seneca.workmanagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

public class VendorShare {
	@ApiModelProperty(notes = "Name of the vendor", name = "name", required = true)
	private String name;

	@ApiModelProperty(notes = "Initial share of each vendor", name = "share", required = false)
	@JsonIgnore
	private int share;

	@ApiModelProperty(notes = "Remaining Share of the total tasks", name = "remainderShare", required = false)
	@JsonIgnore
	int remainderShare;

	@ApiModelProperty(notes = "Percentage of each vendor", name = "percentage", required = false)
	@JsonIgnore
	float percentage;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getShare() {
		return share;
	}

	public void setShare(int share) {
		this.share = share;
	}

	public int getRemainderShare() {
		return remainderShare;
	}

	public void setRemainderShare(int remainderShare) {
		this.remainderShare = remainderShare;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}

	@ApiModelProperty(notes = "Total Calculated share of each vendor", name = "share", required = true)
	@JsonProperty("share")
	public int getCalculatedShare() {
		return this.share + this.remainderShare;
	}

	@JsonProperty("share")
	public void setCalculatedShare(int share) {
		this.share = share;
	}
}
