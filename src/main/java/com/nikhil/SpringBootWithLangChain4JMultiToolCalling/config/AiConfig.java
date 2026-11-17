package com.nikhil.SpringBootWithLangChain4JMultiToolCalling.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nikhil.SpringBootWithLangChain4JMultiToolCalling.assistant.OrderAssistant;
import com.nikhil.SpringBootWithLangChain4JMultiToolCalling.tools.CustomerTools;
import com.nikhil.SpringBootWithLangChain4JMultiToolCalling.tools.OrderTools;
import com.nikhil.SpringBootWithLangChain4JMultiToolCalling.tools.PaymentTools;
import com.nikhil.SpringBootWithLangChain4JMultiToolCalling.tools.ShipmentTools;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;

@Configuration
public class AiConfig {

    @Bean
    public ChatModel chatModel(
            @Value("${app.ai.openai.api-key}") String apiKey,
            @Value("${app.ai.openai.model-name}") String modelName) {

        return OpenAiChatModel.builder()
                .apiKey(apiKey)
                .modelName(modelName)
                .temperature(0.2)
                .build();
    }

    @Bean
    public OrderAssistant orderAssistant(
            ChatModel chatModel,
            OrderTools orderTools,
            PaymentTools paymentTools,
            CustomerTools customerTools,
            ShipmentTools shipmentTools) {

        return AiServices.builder(OrderAssistant.class)
                .chatModel(chatModel)
                .tools(
                        orderTools,
                        paymentTools,
                        customerTools,
                        shipmentTools
                )
                .build();
    }
}