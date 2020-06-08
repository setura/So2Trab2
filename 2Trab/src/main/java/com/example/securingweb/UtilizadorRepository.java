package com.example.securingweb;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UtilizadorRepository extends CrudRepository<Utilizador, Long> {

    Utilizador findByuserName(String name);
}
