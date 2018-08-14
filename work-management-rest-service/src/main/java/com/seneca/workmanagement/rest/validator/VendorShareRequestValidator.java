package com.seneca.workmanagement.rest.validator;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.seneca.workmanagement.model.Vendor;
import com.seneca.workmanagement.model.VendorShareRequest;

@Component
public class VendorShareRequestValidator implements Validator {
	@Override
	public boolean supports(Class<?> type) {
		return VendorShareRequest.class.isAssignableFrom(type);
	}

	/**
	 * Will validate the vendor share request and would update the status with error
	 * messages if validation fails.
	 */
	@Override
	public void validate(Object obj, Errors errors) {
		if (obj != null) {
			VendorShareRequest shareRequest = (VendorShareRequest) obj;
			validateTotalTasks(shareRequest, errors);
			validateVendors(shareRequest, errors);
		} else {
			errors.rejectValue("vendors", "vendors.request.body.empty.error",
					"Vendor share request body should not be empty");
		}
	}

	/**
	 * Will validate the vendors details with total percentage and vendor names.
	 * 
	 * @param shareRequest
	 *            the vendor share request.
	 * @param errors
	 *            the error message if validation fails.
	 */
	private void validateVendors(VendorShareRequest shareRequest, Errors errors) {
		if (shareRequest.getVendors() == null || shareRequest.getVendors().isEmpty()) {
			errors.rejectValue("vendors", "vendors.empty.error", "Vendors should not be empty");
		} else {
			validateVendorsName(shareRequest, errors);
			validateVendorsPercentage(shareRequest, errors);
		}
	}

	/**
	 * Will validate the vendor names.
	 * 
	 * @param shareRequest
	 *            the vendor share request.
	 * @param errors
	 *            the error message if validation fails.
	 */
	private void validateVendorsName(VendorShareRequest shareRequest, Errors errors) {
		for (Vendor vendor : shareRequest.getVendors()) {
			if (isEmpty(vendor.getName())) {
				errors.rejectValue("vendors", "vendors.tasks.name.error", "Vendor name cannot be empty.");
				break;
			}
		}
	}

	/**
	 * Will validate the total vendors percentages with below cases: <br>
	 * 1. Total percentage should not be less than 100.<br>
	 * 2. Total percentage should not be greater than 100.<br>
	 * 
	 * @param shareRequest
	 *            the vendor share request.
	 * @param errors
	 *            the error message if validation fails.
	 */
	private void validateVendorsPercentage(VendorShareRequest shareRequest, Errors errors) {
		float totalPercentage = 0f;
		for (Vendor vendor : shareRequest.getVendors()) {
			totalPercentage += vendor.getPercentage();
		}
		if (Float.compare(totalPercentage, 100f) != 0) {
			errors.rejectValue("vendors", "vendors.total.percentage.error",
					"The total percentage should be equal to 100");
		}
	}

	/**
	 * Will validate total tasks with below cases: 1.
	 * 
	 * @param shareRequest
	 * @param errors
	 */
	private void validateTotalTasks(VendorShareRequest shareRequest, Errors errors) {
		if (shareRequest.getTotalTasks() <= 0) {
			errors.rejectValue("totalTasks", "total.tasks.zero.error", "Total task count should be greater than zero");
		}
	}

	/**
	 * Will validate if the provided username is null or empty.
	 * 
	 * @param value
	 *            the value to be validated.
	 * 
	 * @return with return the operation status as true or false
	 */
	public boolean isEmpty(String value) {
		return StringUtils.isEmpty(value);
	}
}