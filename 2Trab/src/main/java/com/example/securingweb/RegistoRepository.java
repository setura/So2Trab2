package com.example.securingweb;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

    public interface RegistoRepository extends CrudRepository<Registo, Long> {

    Registo findBylocalName(String name);

    Iterable<Registo> findByuserId(String id);

    @Query(
            value = "SELECT DISTINCT r.local_name, r.longitude, r.latitude,r.reg_type, Count( r.reg_type ) " +
                    " From Registo as r " +
                    " GROUP BY r.local_name, r.longitude, r.latitude,r.reg_type " +
                    " ORDER BY r.local_name, r.longitude, r.latitude",
            //value = "SELECT * From registo",
                nativeQuery = true)
    Iterable<Registo> getTest();


}
