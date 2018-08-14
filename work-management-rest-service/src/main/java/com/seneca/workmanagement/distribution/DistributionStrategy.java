package com.seneca.workmanagement.distribution;

import java.util.List;

import com.seneca.workmanagement.model.Vendor;
import com.seneca.workmanagement.model.VendorShare;

public interface DistributionStrategy {
	public List<VendorShare> distribute(List<Vendor> vendors, int totalItems);
}
