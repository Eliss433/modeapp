package com.example.backend.user;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import com.example.backend.item.*;


@Entity
@Table (name = "users")
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

    public void setWardrobe(ArrayList<Item> items){
        this.items = items;
    }

    public Long getId() {
        return id;
    }
}

