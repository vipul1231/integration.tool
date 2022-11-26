package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.MessageChannels;
import org.springframework.integration.dsl.Pollers;
import org.springframework.messaging.MessageChannel;

import java.time.Duration;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

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

    @MessagingGateway
    public interface TestBridge {

        @Gateway(requestChannel = "bridge.input")
        Collection<String> bridge(Collection<String> inputs);
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

    @Bean
    public IntegrationFlow bridge() {
        System.out.println("BRIDGE");
        return f -> f.split().<String, String>transform(String::toUpperCase)
                .aggregate().channel("connect");
    }

    @Bean
    public IntegrationFlow connectBdg() {
        return IntegrationFlow.from("connect")
                .bridge(e -> e.poller(Pollers.fixedRate(2000)))
                .handle(i -> System.out.println(i.getPayload())).get();
    }

    @Bean
    public MessageChannel connect() {
        return MessageChannels.queue().get();
    }
}
