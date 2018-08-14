package com.seneca.workmanagement.model;

import io.swagger.annotations.ApiModelProperty;

public class Vendor {
	@ApiModelProperty(notes = "Name of the vendor", name = "name", required = true)
	private String name;
	@ApiModelProperty(notes = "Percentage of each vendor", name = "percentage", required = true)
	private float percentage;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getPercentage() {
		return percentage;
	}

	public void setPercentage(float percentage) {
		this.percentage = percentage;
	}
}
