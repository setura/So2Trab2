package com.example.securingweb;

import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

    public interface RegistoRepository extends CrudRepository<Registo, Long> {

    Registo findBylocalName(String name);

    Iterable<Registo> findByuserId(String id);

        @Query(value = "Select DISTINCT * from registo",
                nativeQuery = true)
    Iterable<Registo> findAllDistinct();



    @Query(value = "delete from registo where date < now() - interval '1 hour'",
    nativeQuery = true)
    void deleteLastHour() throws Exception;


    @Query(
            value = "SELECT DISTINCT new com.example.securingweb.Registo(r.localName, r.longitude, r.latitude,r.regType, Count( r.regType )) " +
                    " From Registo r " +
                    " GROUP BY r.localName, r.longitude, r.latitude,r.regType " +
                    " ORDER BY r.localName, r.longitude, r.latitude",
            nativeQuery = false
            )

            List<Registo> getTest();

   /*
        Iterable<Registo> findDistinctByRegIdAndLocalNameAndLongitudeAndLatitudeAndRegTypeAndCountAndRegType
                (String local,int longitude,int latitude,int type,long cont);
*/
    }
