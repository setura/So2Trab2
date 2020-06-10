package com.example.securingweb;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

    public interface RegistoRepository extends CrudRepository<Registo, Long> {

    Registo findBylocalName(String name);

    Iterable<Registo> findByuserId(String id);

    @Query("SELECT DISTINCT local_name, longitude, latitude,reg_type, Count(reg_type) From registo GROUP BY local_name, longitude, latitude,reg_type ORDER BY local_name, longitude, latitude")
    List<Registo> getTest();


}
