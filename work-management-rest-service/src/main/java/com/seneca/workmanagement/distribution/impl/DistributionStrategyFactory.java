package com.seneca.workmanagement.distribution.impl;

import com.seneca.workmanagement.distribution.DistributionStrategy;
import com.seneca.workmanagement.distribution.DistributionMethod;

public class DistributionStrategyFactory {
	private DistributionStrategyFactory() {

	}

	public static DistributionStrategy getDistributionAlgorithm(DistributionMethod method) {
		DistributionStrategy strategy = null;
		if (DistributionMethod.PERCENTAGE == method) {
			strategy = new PercentageDistributionStrategy();
		} else if (DistributionMethod.ROUNDOFF == method) {
			strategy = new PercentageRoundoffStrategy();
		}
		return strategy;
	}
}
