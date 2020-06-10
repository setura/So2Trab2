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
    long regId;
    String userId;
    int regType;// 0,vazio ;1,poucas pessoas; 2,cheio; 3, cheio com fila
    double longitude;
    double latitude;
    String localName;
    Date date;
    long count;
    //SimpleDateFormat parser = new SimpleDateFormat("HH:mm");

    public Registo(){}

    public Registo(String userId,int type,double longitude,double latitude,String name)
    {
        this.userId = userId;
        this.regType = type;
        this.longitude= longitude;
        this.latitude = latitude;
        this.localName = name;
        this.date = new Date();
    }
    public Registo(long regId,String name,double longitude,double latitude,int type,long count)
    {
        this.regType = type;
        this.longitude= longitude;
        this.latitude = latitude;
        this.localName = name;
        this.count=count;
    }

    public long getRegId() {
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

    public String getUserId() {
        return userId;
    }

    public String decodeStituation(int regType)
    {
        switch (regType){
            case 0:
                return "Vazio ou com mínima lotação";

            case 1:
                return "com pessoas, mas espaço suficiente";

            case 2:
                return "Muito cheio";

            case 3:
                return "Muito cheio e com fila de espera";

            default:
                return "Situação não conhecida";

        }
    }


}
