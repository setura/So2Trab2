package com.example.securingweb;

public class Votes {

    int reg_type;// 0,vazio ;1,poucas pessoas; 2,cheio; 3, cheio com fila
    double longitude;
    double latitude;
    String local_name;
    int count;

    public Votes(){}

    public Votes(int reg_type, double longitude, double latitude, String local_name, int count)
    {
        this.reg_type=reg_type;
        this.longitude=longitude;
        this.latitude=latitude;
        this.local_name=local_name;
        this.count=count;
    }


}
