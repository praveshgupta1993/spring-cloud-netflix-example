package com.nagarro.nagp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.nagp.hystrix.HytrixWrappedServiceBClient;

@RestController
public class AServiceController {

	@Value("${name:unknown}")
	private String name;

	@Autowired
	private HytrixWrappedServiceBClient serviceBClient;

	@Autowired
	private Registration registration;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printServiceA() {

		return registration.getServiceId() + " (" + registration.getHost() + ":" + registration.getPort() + " )"
				+ "===>name:" + name + "<br/>" + serviceBClient.printServiceB();
	}

}
