package com.boe.posture;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@MapperScan("com.boe.posture.dao")
@EnableTransactionManagement
@EnableScheduling
//@ServletComponentScan(basePackages = "com.jrtc.stamina.listener")
public class PostureApplication {

	public static void main(String[] args) {
		SpringApplication.run(PostureApplication.class, args);
	}

}
