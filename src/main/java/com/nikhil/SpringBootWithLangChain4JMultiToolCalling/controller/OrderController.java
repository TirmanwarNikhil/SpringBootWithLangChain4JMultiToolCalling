package com.nikhil.SpringBootWithLangChain4JMultiToolCalling.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nikhil.SpringBootWithLangChain4JMultiToolCalling.service.OrderAssistantService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

@RestController
@RequestMapping("/api/assistant")
@Tag(name = "AI Multi-Tool Assistant", description = "AI assistant with Order, Payment, Customer and Shipment tools")
public class OrderController {

	private final OrderAssistantService assistantService;

	public OrderController(OrderAssistantService assistantService) {
		this.assistantService = assistantService;
	}

	@PostMapping("/chat")
	@Operation(summary = "Chat with AI Multi-Tool Assistant", description = """
			Ask questions about orders, payments, customers and shipments.
			The AI model automatically selects the appropriate Java tools.
			It can also invoke multiple tools for a single question.
			""")
	@ApiResponse(responseCode = "200", description = "AI assistant response generated successfully", content = @Content(schema = @Schema(implementation = ChatResponse.class)))
	@ApiResponse(responseCode = "400", description = "Invalid request")
	public ChatResponse chat(@Valid @RequestBody ChatRequest request) {

		String answer = assistantService.chat(request.question());

		return new ChatResponse(answer);
	}

	public record ChatRequest(
			@NotBlank @Schema(description = "Question for the AI assistant", example = "Give me complete information about order ORD1001") String question) {
	}

	public record ChatResponse(@Schema(description = "AI-generated answer") String answer) {
	}
}