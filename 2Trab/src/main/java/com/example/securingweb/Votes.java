package com.example.securingweb;


import org.springframework.context.annotation.Configuration;

@Configuration
public class Votes {

    int reg_type;// 0,vazio ;1,poucas pessoas; 2,cheio; 3, cheio com fila
    double longitude;
    double latitude;
    String local_name;
    long count;

    public Votes(){}
    public Votes(String local_name, double longitude, double latitude ,int reg_type,long count)
    {
        this.reg_type=reg_type;
        this.longitude=longitude;
        this.latitude=latitude;
        this.local_name=local_name;
        this.count=count;
    }


}
