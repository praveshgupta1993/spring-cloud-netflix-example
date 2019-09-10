package com.nagarro.nagp.hystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nagarro.nagp.feign.ServiceBClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@Service
public class HytrixWrappedServiceBClient implements ServiceBClient {

	@Autowired
	private ServiceBClient serviceBClient;

	@Override
	@HystrixCommand(groupKey = "helloGroup", fallbackMethod = "fallBackCall")
	public String printServiceB() {
		return serviceBClient.printServiceB();
	}

	public String fallBackCall() {
		return "Failed Service B Call ! - Falling Back";
	}

}
