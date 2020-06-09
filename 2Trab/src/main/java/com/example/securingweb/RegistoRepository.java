package com.example.securingweb;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

    public interface RegistoRepository extends CrudRepository<Registo, Long> {

    Registo findBylocalName(String name);

    Iterable<Registo> findByuserId(String id);

        // findAllByByuserId(long id);

}
