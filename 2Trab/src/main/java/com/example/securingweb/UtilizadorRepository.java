package com.example.securingweb;

import org.springframework.data.repository.CrudRepository;

public interface UtilizadorRepository extends CrudRepository<Utilizador, Long> {

    Utilizador findByuserName(String name);

    boolean existsByUserName(String name);
}
