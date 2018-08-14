package com.seneca.workmanagement.distribution.comparator;

import java.util.Comparator;

import com.seneca.workmanagement.model.Vendor;

public class VendorComparator implements Comparator<Vendor> {
	@Override
	public int compare(Vendor vendor1, Vendor vendor2) {
		if (vendor1.getPercentage() > vendor2.getPercentage()) {
			return 1;
		} else if (vendor1.getPercentage() < vendor2.getPercentage()) {
			return -1;
		}
		return 0;
	}
}
