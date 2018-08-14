package com.seneca.workmanagement.distribution.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.seneca.workmanagement.distribution.DistributionStrategy;
import com.seneca.workmanagement.distribution.comparator.VendorComparator;
import com.seneca.workmanagement.model.Vendor;
import com.seneca.workmanagement.model.VendorShare;

/**
 * This class is responsible to divide the shares based upon the roundoff
 * strategy. Steps Involved are:<br>
 * 1. Maintain Total tasks and remaining tasks with the total items given.<br>
 * 2. Sort the vendor percentages from Low to High.<br>
 * 3. Now among the total sorted items distribute items to vendors who has less
 * share. Here Math.round is used to allocate item if it is in decimals.<br>
 * 3. Now deduct the allocated count from the remaining count until highest
 * share vendor is left and assign the remaining count to the highest share
 * vendor.
 * 
 * @author vinayk
 *
 */
public class PercentageRoundoffStrategy implements DistributionStrategy {
	/**
	 * This class is responsible to divide the shares based upon the roundoff
	 * strategy.
	 */
	@Override
	public List<VendorShare> distribute(List<Vendor> vendors, int totalItems) {
		Collections.sort(vendors, new VendorComparator());
		List<VendorShare> shares = new ArrayList<>();
		int count = 0;
		VendorShare vendorShare;
		for (int ind = 0; ind < vendors.size(); ind++) {
			vendorShare = new VendorShare();
			vendorShare.setName(vendors.get(ind).getName());
			if (ind == vendors.size() - 1) {
				vendorShare.setShare(totalItems - count);
			} else {
				vendorShare.setShare(Math.round((vendors.get(ind).getPercentage() / 100) * totalItems));
			}
			count += vendorShare.getShare();
			shares.add(vendorShare);
		}
		return shares;
	}
}
