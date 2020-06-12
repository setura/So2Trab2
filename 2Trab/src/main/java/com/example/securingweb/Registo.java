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
    long emp_ty, few_people,fu_ll,full_w_queue;
    double dist;


    double latitude;
    double longitude;
    String localName;
    Date date;
    //SimpleDateFormat parser = new SimpleDateFormat("HH:mm");

    public Registo(){}

    public Registo( String userId, double latitude, double longitude, String localName,int type) {
        this.userId = userId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.localName = localName;
        this.date = new Date();
        switch (type)
        {
            case 0:
                this.emp_ty=1;
                this.few_people=0;
                this.fu_ll=0;
                this.full_w_queue=0;
                break;
            case 1:
                this.emp_ty=0;
                this.few_people=1;
                this.fu_ll=0;
                this.full_w_queue=0;
                break;
            case 2:
                this.emp_ty=0;
                this.few_people=0;
                this.fu_ll=1;
                this.full_w_queue=0;
                break;
            case 3:
                this.emp_ty=0;
                this.few_people=0;
                this.fu_ll=0;
                this.full_w_queue=1;
                break;
        }
    }


    public Registo(String localName,double latitude, double longitude,long emp_ty, long few_people, long fu_ll, long full_w_queue) {
        this.emp_ty = emp_ty;
        this.few_people = few_people;
        this.fu_ll = fu_ll;
        this.full_w_queue = full_w_queue;
        this.latitude = latitude;
        this.longitude = longitude;
        this.localName = localName;
    }
    public Registo(String localName,double latitude, double longitude,double dist) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.localName = localName;
        this.dist=dist;
    }

    /*public Registo(String name,double longitude,double latitude,int type,long count)
    {
        this.regType = type;
        this.longitude= longitude;
        this.latitude = latitude;
        this.localName = name;
        this.count=count;
    }*/

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

    public Date getDate() {
        return date;
    }

    public String getUserId() {
        return userId;
    }

    public String decodeStituation()
    {

        if(this.emp_ty==1)
            return "Empty";

        if(this.few_people==1)
                return "Only a few people";

        if(this.fu_ll==1)
                return "Full";

        if(this.full_w_queue==1)
                return "Full with queue";

        return "Unknown Situation";

    }


    public int compareTo(Registo o) {
        // usually toString should not be used,
        // instead one of the attributes or more in a comparator chain
        return toString().compareTo(o.toString());
    }


}
