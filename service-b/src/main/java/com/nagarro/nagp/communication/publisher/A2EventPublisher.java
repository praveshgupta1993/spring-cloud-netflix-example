package com.nagarro.nagp.communication.publisher;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.nagp.communication.event.A2Event;

@Component
public class A2EventPublisher {

	private static final String TOPIC_EXCHANGE_NAME = "spring-boot-exchange";
	private static final String ROUTE_URL = "spring-boot-route";

	@Autowired
	private RabbitTemplate rabbitTemplate;

	public void publishEvent(final A2Event event) {
		System.out.println("Sending message from service-B for object {" + event.getId() + ":" + event.getAge() + "}");
		try {
			rabbitTemplate.convertAndSend(TOPIC_EXCHANGE_NAME, ROUTE_URL, new ObjectMapper().writeValueAsString(event));
		} catch (AmqpException | JsonProcessingException e) {
			e.printStackTrace();
		}
	}

}
