package com.arextest.agent.test;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author yongwuhe
 */
@SpringBootApplication
@MapperScan(basePackages = "com.arextest.agent.test.mapper")
public class ArexAgentTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArexAgentTestApplication.class, args);
	}

}
