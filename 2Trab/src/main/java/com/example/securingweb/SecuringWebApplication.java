package com.example.securingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Period;
import java.util.Date;

@SpringBootApplication
public class SecuringWebApplication implements CommandLineRunner {

	SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
	Duration time;



	@Autowired
	public RegistoRepository regRepository;
	@Autowired
	public UtilizadorRepository userRepository;


	public static void main(String[] args) throws Throwable {
		SpringApplication.run(SecuringWebApplication.class, args);
	}

	@Override
	public void run(String... args) {
		System.out.println("StartApplication...");
		if (userRepository.existsByUserName("admin"))
			System.out.println("Admin active!!!");
	 	else{
			userRepository.save(new Utilizador("admin","$2a$10$o1KV6W18v5HCXJInI4E3cO/85gjmL5Wv/MMzRzKTB6Ebxu/Od1gF6","RULE_ADMIN"));
			System.out.println("New Admin active!!!");
	 	}
	}
	public RegistoRepository getRegRepository() {
		return regRepository;
	}

	public UtilizadorRepository getUserRepository() {
		return userRepository;
	}


}
