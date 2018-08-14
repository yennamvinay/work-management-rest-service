package com.seneca.workmanagement.rest.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import com.seneca.workmanagement.config.WorkManagementRestServiceApplication;
import com.seneca.workmanagement.model.Vendor;
import com.seneca.workmanagement.model.VendorShareRequest;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = { WorkManagementRestServiceApplication.class })
public class VendorShareRequestValidatorTest {
	/** The VendorShareRequestValidator */
	@Autowired
	private VendorShareRequestValidator vendorShareRequestValidator;

	/** The errors */
	private Errors errors;

	/** The vendor share request */
	private VendorShareRequest validVendorShareRequest;

	/**
	 * Creates necessary inputs for the execution of test cases.
	 */
	@Before
	public void setup() {
		validVendorShareRequest = buildVendorWorkShareRequest("A", 15, 35.5f);
		errors = new BeanPropertyBindingResult(validVendorShareRequest, "vendorShareRequest");
	}

	/**
	 * Will validate the vendors with valid username and has no errors.
	 */
	@Test
	public void testValidate_withValidUsername() {
		vendorShareRequestValidator.validate(validVendorShareRequest, errors);
		assertFalse(errors.hasFieldErrors());
	}

	/**
	 * Will validate the sum of vendors percentage and has no errors.
	 */
	@Test
	public void testValidate_withValidPercentage() {
		vendorShareRequestValidator.validate(validVendorShareRequest, errors);
		assertFalse(errors.hasFieldErrors());
	}

	/**
	 * Will validate the total tasks entry and has no errors.
	 */
	@Test
	public void testValidate_withValidTotalTasks() {
		vendorShareRequestValidator.validate(validVendorShareRequest, errors);
		assertFalse(errors.hasFieldErrors());
	}

	/**
	 * Will validate the total tasks entry and has no errors.
	 */
	@Test
	public void testValidate_withInValidTotalTasks() {
		vendorShareRequestValidator.validate(buildVendorWorkShareRequest("A", 0, 35.5f), errors);
		assertEquals("Total task count should be greater than zero",
				errors.getFieldError("totalTasks").getDefaultMessage());
	}

	/**
	 * Will validate the total tasks entry and has no errors.
	 */
	@Test
	public void testValidate_withNullRequest() {
		vendorShareRequestValidator.validate(null, errors);
		assertEquals("Vendor share request body should not be empty",
				errors.getFieldError("vendors").getDefaultMessage());
	}

	/**
	 * Will validate the total tasks entry and has no errors.
	 */
	@Test
	public void testValidate_withInValidPercentage() {
		vendorShareRequestValidator.validate(buildVendorWorkShareRequest("A", 15, 45.5f), errors);
		assertEquals("The total percentage should be equal to 100",
				errors.getFieldError("vendors").getDefaultMessage());
	}

	/**
	 * Will validate the total tasks entry and has no errors.
	 */
	@Test
	public void testValidate_withInValidName() {
		vendorShareRequestValidator.validate(buildVendorWorkShareRequest("", 15, 35.5f), errors);
		assertEquals("Vendor name cannot be empty.", errors.getFieldError("vendors").getDefaultMessage());
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
		request.setTotalTasks(totalTasks);
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
}
