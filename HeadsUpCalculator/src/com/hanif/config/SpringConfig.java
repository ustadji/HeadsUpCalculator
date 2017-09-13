package com.hanif.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.hanif.controller, com.hanif.validator, com.hanif.service"})
public class SpringConfig extends WebMvcConfigurerAdapter {


}
