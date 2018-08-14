package com.seneca.workmanagement.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import com.seneca.workmanagement.config.WorkManagementRestServiceApplication;
import com.seneca.workmanagement.distribution.DistributionMethod;
import com.seneca.workmanagement.model.Vendor;
import com.seneca.workmanagement.model.VendorShare;
import com.seneca.workmanagement.rest.exception.ExecutionException;
import com.seneca.workmanagement.rest.exception.ResourceNotFoundException;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { WorkManagementRestServiceApplication.class })
public class WorkLoadServiceTest {
	@Autowired
	private WorkLoadService workLoadService;
	private List<Vendor> vendors = new ArrayList<>();

	/**
	 * Creates necessary inputs for the execution of test cases.
	 */
	@Before
	public void setUp() {
		vendors = buildVendorList();
	}

	/**
	 * Will test fetching of vendor share when distribution method with valid inputs
	 * and proper response.
	 * 
	 * @throws Exception
	 *             the exception.
	 */
	@Test
	public void getTasksListPerVendor_whenPercentageDistribution() throws Exception {
		List<VendorShare> shares = workLoadService.distributeTasks(DistributionMethod.PERCENTAGE, vendors, 15);
		assertNotNull(shares);
		assertTrue(shares.size() == 3);
		assertTrue(shares.get(0).getCalculatedShare() == 6);
		assertTrue(shares.get(1).getCalculatedShare() == 7);
		assertTrue(shares.get(2).getCalculatedShare() == 2);
	}

	/**
	 * Will test fetching of vendor share when distribution method is Null and
	 * expected result is ExecutionException.
	 * 
	 * @throws Exception
	 *             the exception.
	 */
	@Test(expected = ResourceNotFoundException.class)
	public void getTasksListPerVendor_whenInvalidDistribution() throws Exception {
		workLoadService.distributeTasks(null, vendors, 15);
	}

	/**
	 * Will test fetching of vendor share when empty vendors are sent and expected
	 * result is ExecutionException.
	 * 
	 * @throws Exception
	 *             the exception.
	 */
	@Test(expected = ExecutionException.class)
	public void getTasksListPerVendor_whenNoVendors() throws Exception {
		workLoadService.distributeTasks(DistributionMethod.PERCENTAGE, new ArrayList<Vendor>(), 15);
	}

	/**
	 * Will build list of vendor objects.
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
