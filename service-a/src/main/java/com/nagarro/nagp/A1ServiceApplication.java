package com.nagarro.nagp;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

import com.nagarro.nagp.communication.listener.A1EventReceiver;

@SpringBootApplication
@EnableEurekaClient
@RefreshScope
@EnableFeignClients
@EnableHystrix
public class A1ServiceApplication {

	private static final String QUEUE_NAME = "spring-boot";
	private static final String TOPIC_EXCHANGE_NAME = "spring-boot-exchange";
	private static final String ROUTE_URL = "spring-boot-route";

	@Configuration
	public static class SecurityPermitAllConfig extends WebSecurityConfigurerAdapter {
		@Override
		protected void configure(HttpSecurity http) throws Exception {
			http.authorizeRequests().anyRequest().permitAll().and().csrf().disable();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(A1ServiceApplication.class, args);
	}

	@Bean
	Queue queue() {
		return new Queue(QUEUE_NAME);
	}

	@Bean
	TopicExchange topicExchange() {
		return new TopicExchange(TOPIC_EXCHANGE_NAME);
	}

	@Bean
	Binding binding(final Queue queue, final TopicExchange topicExchange) {
		return BindingBuilder.bind(queue).to(topicExchange).with(ROUTE_URL);
	}

	@Bean
	SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
			MessageListenerAdapter messageListenerAdapter) {
		SimpleMessageListenerContainer messageListenerContainer = new SimpleMessageListenerContainer();
		messageListenerContainer.setConnectionFactory(connectionFactory);
		messageListenerContainer.setQueueNames(QUEUE_NAME);
		messageListenerContainer.setMessageListener(messageListenerAdapter);
		return messageListenerContainer;
	}

	@Bean
	MessageListenerAdapter listenerAdapter(final A1EventReceiver a1EventReceiver) {
		return new MessageListenerAdapter(a1EventReceiver, "receiveMessage");
	}

}
