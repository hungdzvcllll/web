package com.web.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.authentication.AuthenticationProvider;

@SpringBootApplication (scanBasePackages = {
"com.web.web.security","com.web.web.mapper","com.web.web.controller","com.web.web.service","com.web.web.service.impl","com.web.web.config" })
public class WebApplication {
	@Autowired
	AuthenticationProvider provider;

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

}
