package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;

import java.util.Collection;

@EnableIntegration
@Configuration
public class EchoFlow {

    @Autowired
    private CustomAppend customAppend;

    @MessagingGateway
    public interface Upcase {

        @Gateway(requestChannel = "upcase.input")
        Collection<String> upcase(Collection<String> strings);

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

}
