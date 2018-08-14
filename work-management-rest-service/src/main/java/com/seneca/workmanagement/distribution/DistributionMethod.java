package com.seneca.workmanagement.distribution;

public enum DistributionMethod {
	PERCENTAGE("percentage"), ROUNDOFF("roundoff");

	private String id;

	private DistributionMethod(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public static DistributionMethod getById(String id) {
		DistributionMethod method = null;
		for (DistributionMethod entry : values()) {
			if (entry.getId().equals(id)) {
				method = entry;
				break;
			}
		}
		return method;
	}
}
