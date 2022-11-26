package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.Gateway;
import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;

import java.util.Collection;

@EnableIntegration
@Configuration
public class EchoFlow {

    @MessagingGateway
    public interface Upcase {

        @Gateway(requestChannel = "upcase.input")
        Collection<String> upcase(Collection<String> strings);

        @Gateway(requestChannel = "append.input")
        String appendName();

    }

    @Bean
    public IntegrationFlow upcase() {
        return f -> f
                .split()
                .<String, String>transform(String::toUpperCase).
                .aggregate().channel("append.input");
    }

    @Bean
    public IntegrationFlow appendData() {
        return f -> f.handle("EchoFlow","append");
    }


    public String append(String name) {
        return name+"append";
    }


}
