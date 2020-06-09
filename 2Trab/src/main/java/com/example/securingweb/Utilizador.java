package com.example.securingweb;

import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Utilizador {

    @Id
    private String userName;
    private String password;
    private String role;
    private Boolean enabled;


    public Utilizador(){}
    public Utilizador(String userName, String password,String role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.enabled=true;

    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

}
