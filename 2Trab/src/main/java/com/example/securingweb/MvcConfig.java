package com.example.securingweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/hello").setViewName("hello");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/locals").setViewName("locals");
		registry.addViewController("/css/table_css.css").setViewName("css/table_css.css");
		//registry.addViewController("/locals/add").setViewName("home");
		registry.addViewController("/add").setViewName("add");
		registry.addViewController("/userPanel").setViewName("userPanel");
		registry.addViewController("/delete").setViewName("delete");
		registry.addViewController("/css/login_css.css").setViewName("css/login_css.css");
		registry.addViewController("/css/add_css.css").setViewName("css/add_css.css");
		registry.addViewController("/adduser").setViewName("adduser");
		registry.addViewController("/near").setViewName("near");
		registry.addViewController("/near/get").setViewName("near/get");
	}

}
