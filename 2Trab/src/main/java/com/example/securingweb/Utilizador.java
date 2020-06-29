package com.example.securingweb;

import javax.persistence.Entity;
import javax.persistence.Id;


@Entity
public class Utilizador {

    @Id
    private String userName;
    private String password;
    private String role;
    private Boolean enabled;


    public Utilizador() {
    }

    public Utilizador(String userName, String password, String role) {
        this.userName = userName;
        this.password = password;
        this.role = role;
        this.enabled = true;

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
