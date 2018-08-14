package com.seneca.workmanagement.distribution.impl;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.seneca.workmanagement.model.Vendor;
import com.seneca.workmanagement.model.VendorShare;

public class PercentageRoundoffStrategyTest {
	/** The PercentageRoundoffStrategy object */
	private PercentageRoundoffStrategy percentageRoundoffStrategy;

	/**
	 * Creates necessary inputs for the execution of test cases.
	 */
	@Before
	public void setup() {
		percentageRoundoffStrategy = new PercentageRoundoffStrategy();
	}

	/**
	 * Will test fetching of vendor shares when distribution method "roundoff" and
	 * with valid inputs and proper response.
	 * 
	 * @throws Exception
	 *             the exception.
	 */
	@Test
	public void testPercentageStrategyDistribution() {
		List<VendorShare> shares = percentageRoundoffStrategy.distribute(buildVendorList(), 15);
		assertNotNull(shares);
		assertTrue(shares.size() == 3);
		assertTrue(shares.get(0).getCalculatedShare() == 3);
		assertTrue(shares.get(1).getCalculatedShare() == 5);
		assertTrue(shares.get(2).getCalculatedShare() == 7);
	}

	/**
	 * Will build list of vendor objects with valid details.
	 * 
	 * @return vendor objects list.
	 */
	private List<Vendor> buildVendorList() {
		List<Vendor> vendors = new ArrayList<>();
		Vendor vendor = new Vendor();
		vendor.setName("C");
		vendor.setPercentage(35.5f);
		vendors.add(vendor);

		vendor = new Vendor();
		vendor.setName("B");
		vendor.setPercentage(45.5f);
		vendors.add(vendor);

		vendor = new Vendor();
		vendor.setName("A");
		vendor.setPercentage(19.0f);
		vendors.add(vendor);

		return vendors;
	}
}
