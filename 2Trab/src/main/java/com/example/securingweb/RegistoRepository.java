package com.example.securingweb;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface RegistoRepository extends CrudRepository<Registo, Long> {

    Registo findBylocalName(String name);

    Iterable<Registo> findByuserId(String id);


    @Query(
            value = "SELECT DISTINCT new com.example.securingweb.Registo(r.localName, r.latitude,r.longitude,SQRT( POWER(?1 -latitude,2)+ POWER((?2-longitude),2)))\n" +
                    "From Registo r\n" +
                    "GROUP BY r.localName, r.longitude , r.latitude\n" +
                    "ORDER BY r.localName, r.longitude , r.latitude\n",

            nativeQuery = false)
    Iterable<Registo> findClossest(double lat, double longitude) throws Exception;


    @Query(value = "delete from registo where date < now() - interval '1 hour'",
            nativeQuery = true)
    void deleteLastHour() throws Exception;


    @Query(
            value = "SELECT DISTINCT new com.example.securingweb.Registo(r.localName, r.latitude,r.longitude, SUM(r.emp_ty), SUM(r.few_people),Sum(r.fu_ll) , Sum(r.full_w_queue))\n" +
                    "From Registo r\n" +
                    "GROUP BY r.localName, r.longitude , r.latitude \n" +
                    "ORDER BY r.localName, r.longitude, r.latitude",
            nativeQuery = false
    )
    List<Registo> getCompleteTable();

        /*@Query(
                value = "SELECT DISTINCT new com.example.securingweb.Registo(r.localName, r.longitude, r.latitude,r.regType, Count( r.regType )) " +
                        " From Registo r " +
                        " GROUP BY r.localName, r.longitude, r.latitude,r.regType " +
                        " ORDER BY r.localName, r.longitude, r.latitude",
                nativeQuery = false
        )

        List<Registo> getTest();
*/
   /*

        Iterable<Registo> findDistinctByRegIdAndLocalNameAndLongitudeAndLatitudeAndRegTypeAndCountAndRegType
                (String local,int longitude,int latitude,int type,long cont);
*/
}
