package com.example.user;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;

import com.example.backend.item.*;



public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @OneToMany(mappedBy = "owner")
    private List<Item> items = new ArrayList<>();

    // Getter und Setter

    public String getUsername(){
        return username;    
    }

    public void setUsername(String name){
        this.username = name;
    }

    public String getMail(){
        return email;    
    }

    public void setMail(String mail){
        this.email = mail;
    }

    public ArrayList<Item> getWardrobe(){
        return new ArrayList<>(items);    
    }

    public void setUsername(ArrayList<Item> items){
        this.items = items;
    }
}

