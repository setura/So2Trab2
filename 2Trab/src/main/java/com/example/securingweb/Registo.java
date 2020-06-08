package com.example.securingweb;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class Registo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    int regId;
    int userId;
    int regType;// 0,vazio ;1,poucas pessoas; 2,cheio; 3, cheio com fila
    double longitude;
    double latitude;
    String localName;
    Date date;
    //SimpleDateFormat parser = new SimpleDateFormat("HH:mm");

    public Registo(){}

    public Registo(int userId,int type,double longitude,double latitude,String name){
        this.userId = userId;
        this.regType = type;
        this.longitude= longitude;
        this.latitude = latitude;
        this.localName = name;
        this.date = new Date();

    }

    public int getRegId() {
        return regId;
    }

    public String getLocalName() {
        return localName;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public int getRegType() {
        return regType;
    }

    public Date getDate() {
        return date;
    }


}
