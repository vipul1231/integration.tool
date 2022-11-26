package com.example.demo;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.integration.annotation.IntegrationComponentScan;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@IntegrationComponentScan
@SpringBootApplication
public class Application {

//	@Autowired
//	private EchoFlow.Upcase echoFlow;

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx = SpringApplication.run(Application.class);
		Collection<String> collection = ctx.getBean(EchoFlow.Upcase.class).upcase(List.of("hello my name is vipul"));
		System.out.println(collection);
	}

//	@PostConstruct
//	public void init() {
//		echoFlow.upcase(Arrays.asList("fdsfsadsad"));
//	}

}
