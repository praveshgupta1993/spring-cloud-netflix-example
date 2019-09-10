package com.nagarro.nagp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.serviceregistry.Registration;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nagarro.nagp.communication.event.A2Event;
import com.nagarro.nagp.communication.publisher.A2EventPublisher;

@RestController
@RefreshScope
public class ServiceB1Controller {

	@Autowired
	private Registration registration;

	@Autowired
	private A2EventPublisher a2EventPublisher;

	@Value("${msg:unknown}")
	private String msg;

	@RequestMapping(value = "/publishEvent", params = { "id", "age" }, method = RequestMethod.GET)
	public String publishEvent(@RequestParam(value = "id", required = true) final String id,
			@RequestParam(value = "age", required = true) final Integer age) {
		A2Event event = new A2Event();
		event.setId(id);
		event.setAge(age);
		a2EventPublisher.publishEvent(event);
		return registration.getServiceId() + " (" + registration.getHost() + ":" + registration.getPort() + " )"
				+ "===>Say " + "Event Published";
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String printServiceB() {
		return registration.getServiceId() + " (" + registration.getHost() + ":" + registration.getPort() + " )"
				+ "===>Say " + msg;
	}

}
