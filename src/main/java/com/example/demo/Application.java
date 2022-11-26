package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@IntegrationComponentScan("com.example.demo")
@EnableIntegration
@SpringBootApplication
public class Application {

//	@Autowired
//	private EchoFlow.Upcase echoFlow;

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class);
		Collection<String> collection = ctx.getBean(EchoFlow.Upcase.class).upcase(List.of("hello my name is vipul","My name is jay"));
		System.out.println(collection);
		Collection<String> collection1 = ctx.getBean(EchoFlow.SubFlow.class).subFlow(List.of(4,5));
		System.out.println("Output: "+collection1);
	}

	@Bean
	public CustomAppend apr() {
		return new CustomAppend();
	}

//	@PostConstruct
//	public void init() {
//		echoFlow.upcase(Arrays.asList("fdsfsadsad"));
//	}

}
