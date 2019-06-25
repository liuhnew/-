package com.jykj;

import com.jykj.util.IdWorker;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@ComponentScan({"org.activiti.rest.diagram", "com.jykj"})
@EnableAutoConfiguration(exclude = { org.activiti.spring.boot.SecurityAutoConfiguration.class,
		org.springframework.boot.autoconfigure.security.SecurityAutoConfiguration.class })
@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
@EnableSwagger2
public class autosupportApplication {

	public static void main(String[] args) {SpringApplication.run(autosupportApplication.class, args);}

	@Bean
	public IdWorker idWorker(){ return new IdWorker(1, 1); }
}
