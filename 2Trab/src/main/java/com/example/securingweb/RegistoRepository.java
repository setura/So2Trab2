package com.example.securingweb;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Collection;
import java.util.List;

    public interface RegistoRepository extends CrudRepository<Registo, Long> {

    Registo findBylocalName(String name);

    Iterable<Registo> findByuserId(String id);





<<<<<<< HEAD
    @Query(
            value = "SELECT DISTINCT new com.example.securingweb.Registo(r.localName, r.longitude, r.latitude,r.regType, Count( r.regType )) " +
=======
            @Query(
            value = "SELECT DISTINCT new com.example.securingweb.Registo(r.regId,r.localName, r.longitude, r.latitude,r.regType, Count( r.regType )) " +
>>>>>>> 46e2b25d09888e21f29eb17862d904d739435b19
                    " From Registo r " +
                    " GROUP BY r.localName, r.longitude, r.latitude,r.regType " +
                    " ORDER BY r.localName, r.longitude, r.latitude",
            nativeQuery = false
            )
<<<<<<< HEAD
    List<Registo> getTest();



=======
            List<Registo> getTest();

   /*
        Iterable<Registo> findDistinctByRegIdAndLocalNameAndLongitudeAndLatitudeAndRegTypeAndCountAndRegType
                (String local,int longitude,int latitude,int type,long cont);
*/
>>>>>>> 46e2b25d09888e21f29eb17862d904d739435b19
    }
