package com.sample.spring.cloud.customer.config;

import com.netflix.loadbalancer.AvailabilityFilteringRule;
import com.netflix.loadbalancer.BestAvailableRule;
import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.WeightedResponseTimeRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfiguration {
	@Bean
	public IRule ribbonRule() {
		return new WeightedResponseTimeRule();
//		return new BestAvailableRule();
//		return new AvailabilityFilteringRule();
	}
}