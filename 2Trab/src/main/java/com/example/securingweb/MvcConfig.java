package com.example.securingweb;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/home").setViewName("home");
		registry.addViewController("/").setViewName("home");
		registry.addViewController("/login").setViewName("login");
		registry.addViewController("/locals").setViewName("locals");
		registry.addViewController("/css/table_css.css").setViewName("css/table_css.css");
		//registry.addViewController("/locals/add").setViewName("home");
		registry.addViewController("/add").setViewName("add");
		registry.addViewController("/delete").setViewName("delete");
		registry.addViewController("/css/login_css.css").setViewName("css/login_css.css");
		registry.addViewController("/css/add_css.css").setViewName("css/add_css.css");
		registry.addViewController("/css/home_css.css").setViewName("css/home_css.css");
		registry.addViewController("/css/delete_css.css").setViewName("css/delete_css.css");
		registry.addViewController("/adduser").setViewName("adduser");
		registry.addViewController("/near").setViewName("near");
		registry.addViewController("/near/get").setViewName("near/get");
		registry.addViewController("../static/images/logo.png").setViewName("images/logo.png");
		registry.addViewController("/css/error_css.css").setViewName("css/error_css.css");
		registry.addViewController("/error").setViewName("error");
	}

}
