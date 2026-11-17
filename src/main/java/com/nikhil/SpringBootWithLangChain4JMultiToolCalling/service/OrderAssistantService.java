package com.nikhil.SpringBootWithLangChain4JMultiToolCalling.service;

import org.springframework.stereotype.Service;

import com.nikhil.SpringBootWithLangChain4JMultiToolCalling.assistant.OrderAssistant;

@Service
public class OrderAssistantService {

	private final OrderAssistant assistant;

	public OrderAssistantService(OrderAssistant assistant) {
		this.assistant = assistant;
	}

	public String chat(String question) {
		return assistant.chat(question);
	}
}