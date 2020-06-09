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
		//ten = parser.parse("10:00");


		System.out.println("StartApplication...");

		// no segundo run, podemos comentar estas 3 linhas
	 	userRepository.save(new Utilizador("admin","$2a$10$cw0.dazD6XsAJmAshhAW4uEUDMIizMkkOuiaC7otl4TdTsTiTQWzO","RULE_ADMIN"));
		userRepository.save(new Utilizador("user","$2a$10$cw0.dazD6XsAJmAshhAW4uEUDMIizMkkOuiaC7otl4TdTsTiTQWzO" , "ROLE_USER"));
		//regRepository.save(new Registo(1, 2, 45.2,23.4, "PizzaHut"));




		System.out.println("\nfindAll()");

		System.out.println(regRepository.findBylocalName("PizzaHut"));

		/*System.out.println("\nfindById(1L)");
		repository.findById(1l).ifPresent(x -> System.out.println(x));

		System.out.println("\nfindByName('Node')");
		repository.findByName("Node").forEach(x -> System.out.println(x));*/

	}
	public RegistoRepository getRegRepository() {
		return regRepository;
	}

	public UtilizadorRepository getUserRepository() {
		return userRepository;
	}


}
