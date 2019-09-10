package com.nagarro.nagp.communication.listener;

import java.io.IOException;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nagarro.nagp.communication.event.A1Event;

@Component
public class A1EventReceiver {

	public void receiveMessage(String message) {
		System.out.println("Event received on service-A");
		try {
			A1Event event = new ObjectMapper().readValue(message, A1Event.class);
			System.out.println("Event Received for object {" + event.getId() + ":" + event.getAge() + "}");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
