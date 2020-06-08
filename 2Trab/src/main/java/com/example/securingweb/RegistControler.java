package com.example.securingweb;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistControler extends  SecuringWebApplication {

    @GetMapping("/home")
    public RegistoRepository getMovies() {
       return regRepository;

    }

}
