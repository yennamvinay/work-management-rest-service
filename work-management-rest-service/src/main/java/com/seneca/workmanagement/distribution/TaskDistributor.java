package com.seneca.workmanagement.distribution;

import java.util.List;

import org.springframework.stereotype.Component;

import com.seneca.workmanagement.model.Vendor;
import com.seneca.workmanagement.model.VendorShare;

/**
 * This class is responsible to invoke distribute method based upon the provided
 * algorithm.
 * 
 * @author Vinay
 */
@Component
public class TaskDistributor {
	public List<VendorShare> getVendorShares(DistributionStrategy algorithm, List<Vendor> vendors, int totalCount) {
		return algorithm.distribute(vendors, totalCount);
	}
}