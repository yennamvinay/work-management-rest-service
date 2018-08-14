package com.seneca.workmanagement.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.seneca.workmanagement.distribution.DistributionStrategy;
import com.seneca.workmanagement.distribution.DistributionMethod;
import com.seneca.workmanagement.distribution.TaskDistributor;
import com.seneca.workmanagement.distribution.impl.DistributionStrategyFactory;
import com.seneca.workmanagement.model.Vendor;
import com.seneca.workmanagement.model.VendorShare;
import com.seneca.workmanagement.rest.exception.ExecutionException;
import com.seneca.workmanagement.rest.exception.ResourceNotFoundException;

@Service
public class WorkLoadService {
	@Autowired
	private TaskDistributor taskDistributor;

	/**
	 * This method will distribute total tasks to all the vendors based upon the
	 * percentage and the distribution method.
	 * 
	 * @param method
	 *            the distribution method type.
	 * @param vendors
	 *            the list of vendor details.
	 * @param totalCount
	 *            total count of tasks
	 * @return the list of vendor shares as response.
	 * @throws ExecutionException
	 *             the exection exception.
	 * @throws ResourceNotFoundException
	 *             the resource not found exception.
	 */
	public List<VendorShare> distributeTasks(DistributionMethod method, List<Vendor> vendors, int totalCount)
			throws ExecutionException, ResourceNotFoundException {
		List<VendorShare> shares = null;
		DistributionStrategy algorithm = DistributionStrategyFactory.getDistributionAlgorithm(method);
		if (algorithm != null) {
			shares = taskDistributor.getVendorShares(algorithm, vendors, totalCount);
		} else {
			throw new ResourceNotFoundException("No algorithm found for provided type");
		}
		if (shares == null || shares.isEmpty()) {
			throw new ExecutionException(String.format("Failed to distribute tasks using method: %s", method.getId()));
		}
		return shares;
	}
}
