package com.seneca.workmanagement.rest.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.seneca.workmanagement.distribution.DistributionMethod;
import com.seneca.workmanagement.model.VendorShare;
import com.seneca.workmanagement.model.VendorShareRequest;
import com.seneca.workmanagement.rest.exception.ExecutionException;
import com.seneca.workmanagement.rest.exception.ResourceNotFoundException;
import com.seneca.workmanagement.rest.exception.ValidationException;
import com.seneca.workmanagement.rest.validator.VendorShareRequestValidator;
import com.seneca.workmanagement.service.WorkLoadService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
public class WorkShareController {
	/** The Work Load Service */
	@Autowired
	private WorkLoadService workLoadService;

	/** Vendor Share Request Validator */
	@Autowired
	private VendorShareRequestValidator shareValidator;

	/**
	 * This API would divide the tasks equally as per the vendor share.
	 * 
	 * @param shareRequest
	 *            the VendorShareRequest with total tasks and list of vendor
	 *            details.
	 * @return the share of items per vendor.
	 * @throws ResourceNotFoundException
	 *             This exception is thrown when no strategy is submitted.
	 * @throws ExecutionException
	 *             This exception is thrown when failed to distribute tasks using
	 *             method.
	 * @throws ValidationException
	 *             This exception is thrown when validation is failed..
	 */
	@ApiOperation(value = "Fetch shares for vendors", tags = "1.0", notes = "This API would divide the tasks equally as per the vendor share.", response = VendorShare.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Output with distributed share information to each vendor is shown. "),
			@ApiResponse(code = 500, message = "When distributing of share are not processed then an error is shown with proper message."),
			@ApiResponse(code = 206, message = "If validation for the given input is failed then an error is shown with proper message."),
			@ApiResponse(code = 404, message = "No valid strategy found for the given method then an error is shown with proper message.") })
	@PostMapping(value = "/tasks/distribute/{method}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendorShare> getTasksListPerVendor(@RequestBody VendorShareRequest shareRequest,
			@PathVariable("method") String method, Errors errors)
			throws ExecutionException, ResourceNotFoundException, ValidationException {
		DistributionMethod distributionMethod = DistributionMethod.getById(method);
		if (distributionMethod == null) {
			throw new ResourceNotFoundException("No strategy exist for method: " + method);
		}
		shareValidator.validate(shareRequest, errors);
		if (errors.hasErrors()) {
			throw new ValidationException(errors.getAllErrors());
		}
		return workLoadService.distributeTasks(distributionMethod, shareRequest.getVendors(),
				shareRequest.getTotalTasks());
	}
}
