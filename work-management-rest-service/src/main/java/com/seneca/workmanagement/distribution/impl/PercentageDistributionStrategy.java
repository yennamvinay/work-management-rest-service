package com.seneca.workmanagement.distribution.impl;

import java.util.ArrayList;
import java.util.List;

import com.seneca.workmanagement.distribution.DistributionStrategy;
import com.seneca.workmanagement.model.Vendor;
import com.seneca.workmanagement.model.VendorShare;

/**
 * This class is responsible to divide the share based upon the equal percentage
 * strategy.
 * 
 * Steps Involved are:<br>
 * 1. Distribute the tasks to each vendor equally based on the percentage and
 * maintain remaining tasks which are left after sharing the tasks.<br>
 * 2. Now remaining tasks are shared based upon their percentages and already
 * allocated shares.
 * 
 * @author Vinay
 */
public class PercentageDistributionStrategy implements DistributionStrategy {
	/**
	 * This method will distribute the tasks to each vendor equally based on the
	 * percentage and maintain remaining tasks which are left after sharing the
	 * tasks.
	 */
	@Override
	public List<VendorShare> distribute(List<Vendor> vendors, int totalItems) {
		int count = 0;
		List<VendorShare> vendorShares = new ArrayList<>(vendors.size());
		VendorShare vendorShare;
		for (Vendor vendor : vendors) {
			int share = Float.valueOf((vendor.getPercentage() / 100) * totalItems).intValue();
			vendorShare = new VendorShare();
			vendorShare.setName(vendor.getName());
			vendorShare.setShare(share);
			vendorShare.setPercentage(vendor.getPercentage());
			vendorShares.add(vendorShare);
			count += share;
		}
		int remainigCount = totalItems - count;
		if (count > 0 && remainigCount > 0) {
			updateRemainderCount(vendorShares, remainigCount);
		}
		return vendorShares;
	}

	/**
	 * Will distribute the remaining tasks to the vendors.
	 * 
	 * @param vendorShares
	 *            the list of vendor shares.
	 * @param remainingCount
	 *            the remaining tasks count.
	 */
	private void updateRemainderCount(List<VendorShare> vendorShares, int remainingCount) {
		VendorShare maxCountVendor = null;
		int count = 0;
		float share = -1f * remainingCount;
		float temp;
		while (count != remainingCount) {
			for (VendorShare vendorShare : vendorShares) {
				temp = ((vendorShare.getPercentage() / 100) * (count + 1)) - (vendorShare.getRemainderShare() + 1);
				if (temp > share) {
					share = temp;
					maxCountVendor = vendorShare;
				}
			}
			if (maxCountVendor != null) {
				maxCountVendor.setRemainderShare(maxCountVendor.getRemainderShare() + 1);
				count++;
			}
		}
	}
}
