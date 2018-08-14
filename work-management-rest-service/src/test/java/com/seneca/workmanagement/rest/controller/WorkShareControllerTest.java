package com.seneca.workmanagement.rest.controller;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seneca.workmanagement.config.WorkManagementRestServiceApplication;
import com.seneca.workmanagement.model.Vendor;
import com.seneca.workmanagement.model.VendorShare;
import com.seneca.workmanagement.model.VendorShareRequest;
import com.seneca.workmanagement.rest.exception.RestExceptionHandler;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { WorkManagementRestServiceApplication.class })
public class WorkShareControllerTest {
	@Autowired
	private WorkShareController workShareController;
	/** The vendor share request */
	private VendorShareRequest validVendorShareRequest;
	private MockMvc mvc;
	private ObjectMapper objectMapper = new ObjectMapper();
	private static final String WORK_LOAD_URI = "/tasks/distribute/{method}";

	/**
	 * Creates necessary inputs for the execution of test cases.
	 */
	@Before
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(workShareController).setControllerAdvice(new RestExceptionHandler())
				.build();
		validVendorShareRequest = buildVendorWorkShareRequest("C", 15, 35.5f);
	}

	/**
	 * Will test the tasks sharing to vendors based on Percentage distribution
	 * method.
	 * 
	 * @throws Exception
	 *             the exception.
	 */
	@Test
	public void getTasksListPerVendor_whenPercentageDistribution() throws Exception {
		MockHttpServletResponse response = post(WORK_LOAD_URI, validVendorShareRequest, "percentage");
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		String resp = new String(response.getContentAsByteArray());
		assertNotNull(resp);
		List<VendorShare> shares = objectMapper.readValue(resp, getVendorShareType());
		assertNotNull(shares);
		assertTrue(shares.size() == 3);
		assertTrue(shares.get(0).getShare() == 6);
		assertTrue(shares.get(1).getShare() == 7);
		assertTrue(shares.get(2).getShare() == 2);
	}

	/**
	 * Will test the tasks sharing to vendors based on Percentage roundoff method.
	 * 
	 * @throws Exception
	 *             the exception.
	 */
	@Test
	public void getTasksListPerVendor_whenRoundoffDistribution() throws Exception {
		MockHttpServletResponse response = post(WORK_LOAD_URI, validVendorShareRequest, "roundoff");
		Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());
		String resp = new String(response.getContentAsByteArray());
		assertNotNull(resp);
		List<VendorShare> shares = objectMapper.readValue(resp, getVendorShareType());
		assertNotNull(shares);
		assertTrue(shares.size() == 3);
		assertTrue(shares.get(0).getShare() == 3);
		assertTrue(shares.get(1).getShare() == 5);
		assertTrue(shares.get(2).getShare() == 7);
	}

	/**
	 * Will test the tasks sharing to vendors based on invalid strategy method.
	 * 
	 * @throws Exception
	 *             the exception.
	 */
	@Test
	public void getTasksListPerVendor_whenInvalidDistributionStrategy() throws Exception {
		MockHttpServletResponse response = post(WORK_LOAD_URI, validVendorShareRequest, "invalidstrategy");
		Assert.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
	}

	/**
	 * Will test the tasks sharing to vendors based on invalid vendor name.
	 * 
	 * @throws Exception
	 *             the exception.
	 */
	@Test
	public void getTasksListPerVendor_whenInvalidVendorName() throws Exception {
		MockHttpServletResponse response = post(WORK_LOAD_URI, buildVendorWorkShareRequest("", 15, 35.5f), "roundoff");
		Assert.assertEquals(HttpStatus.PARTIAL_CONTENT.value(), response.getStatus());
	}

	private JavaType getVendorShareType() {
		return objectMapper.getTypeFactory().constructCollectionType(List.class, VendorShare.class);
	}

	/**
	 * Will build VendorShareRequest object.
	 * 
	 * @param vendorName
	 *            the vendor name.
	 * @param totalTasks
	 *            the total tasks for the request.
	 * @param vendorPercentage
	 *            the percentage of vendor.
	 * @return the VendorShareRequest object.
	 */
	private VendorShareRequest buildVendorWorkShareRequest(String vendorName, int totalTasks, float vendorPercentage) {
		VendorShareRequest request = new VendorShareRequest();
		request.setTotalTasks(15);
		List<Vendor> vendors = new ArrayList<>();
		Vendor vendor = new Vendor();
		vendor.setName(vendorName);
		vendor.setPercentage(vendorPercentage);
		vendors.add(vendor);

		vendor = new Vendor();
		vendor.setName("B");
		vendor.setPercentage(45.5f);
		vendors.add(vendor);

		vendor = new Vendor();
		vendor.setName("A");
		vendor.setPercentage(19.0f);
		vendors.add(vendor);

		request.setVendors(vendors);
		return request;
	}

	/**
	 * Will build the post request.
	 * 
	 * @param url
	 *            the post url.
	 * @param object
	 *            the request body object.
	 * @param method
	 *            the distribution strategy method.
	 * @return the result of the post operation.
	 * @throws Exception
	 *             the exception.
	 */
	private MockHttpServletResponse post(String url, Object object, String method) throws Exception {
		MockHttpServletRequestBuilder builder = MockMvcRequestBuilders.post(url.replace("{method}", method))
				.contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(object));
		MvcResult result = mvc.perform(builder).andReturn();
		return result.getResponse();
	}
}
