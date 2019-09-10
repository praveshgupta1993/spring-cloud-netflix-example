package com.nagarro.nagp.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "service-b")
public interface ServiceBClient {

	@RequestMapping(value = "/", method = RequestMethod.GET)
	String printServiceB();

}
