package com.example.demo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.integration.annotation.IntegrationComponentScan;
import org.springframework.integration.config.EnableIntegration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;
import java.util.List;

@ExtendWith(SpringExtension.class)
@Import(EchoFlow.class)
@ContextConfiguration(classes = ApplicationTests.Config.class)
@EnableIntegration
@IntegrationComponentScan("com.example.demo")
public class ApplicationTests {

	@Autowired
	private ApplicationContext context;


	@TestConfiguration
	public static class Config {
		@Bean
		public CustomAppend apr() {
			return new CustomAppend();
		}
	}

	@Test
	void contextLoads() {
		Collection<String> collection = context.getBean(EchoFlow.Upcase.class).upcase(List.of("hello my name is vipul","My name is jay"));
		Assertions.assertEquals(2, collection.size());
	}
}
