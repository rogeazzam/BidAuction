package com.bidauc.BidAuctions;

import com.sun.istack.NotNull;

import javax.persistence.*;

@Entity
@Table(name = "users")
public class UserClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NotNull
    private String first_name;

    @NotNull
    private String last_name;

    public UserClass(){

    }

    public UserClass(String first_name, String last_name){
        this.first_name=first_name;
        this.last_name=last_name;
    }
}
