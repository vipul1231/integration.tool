package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;

import java.util.Collection;

@Configuration
public class EchoFlow {

    @Autowired
    private CustomAppend customAppend;

    @MessagingGateway
    public interface Upcase {

        @Gateway(requestChannel = "upcase.input")
        Collection<String> upcase(Collection<String> strings);
    }

    @MessagingGateway
    public interface SubFlow {

        @Gateway(requestChannel = "subflow.input")
        Collection<String> subFlow(Collection<Integer> integers);
    }

    @Bean
    public IntegrationFlow upcase() {
        return f -> f
                .split()
                .<String, String>transform(String::toUpperCase)
                .aggregate().channel("append.input");
    }


    @Bean
    public IntegrationFlow append() {
        return IntegrationFlow.from("append.input").split().handle("apr","app").aggregate().get();
    }

    @Bean
    public IntegrationFlow subflow() {
        System.out.println("Starting subflow....");
        return f -> f
                .split()
                .<Integer, Boolean>route(o -> o % 2 == 0,
                        m -> m
                                .subFlowMapping(true, oddFlow())
                                .subFlowMapping(false, sf -> sf.gateway(evenFlow())))
                .aggregate();
    }

    @Bean
    public IntegrationFlow oddFlow() {
        System.out.println("ODD FLOW CALLED");
        return f -> f.handle(m -> System.out.println("odd"));
    }

    @Bean
    public IntegrationFlow evenFlow() {
        System.out.println("EVEN FLOW CALLED");
        return f -> f.handle(m -> System.out.println("even"));
    }

}
