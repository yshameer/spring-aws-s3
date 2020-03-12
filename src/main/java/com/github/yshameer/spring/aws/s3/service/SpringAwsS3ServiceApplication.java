package com.github.yshameer.spring.aws.s3.service;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@ComponentScan(basePackages = "com.shameer.spring.aws.s3.service")
public class SpringAwsS3ServiceApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SpringAwsS3ServiceApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
	}
}
