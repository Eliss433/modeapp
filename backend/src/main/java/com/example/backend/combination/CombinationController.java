package com.example.backend.combination;

import com.example.backend.item.Item;
import com.example.backend.item.Occasion;
import com.example.backend.item.Season;
import com.example.backend.user.User;
/*
| Import | Nutzung
| Item | Eine Kombination besteht aus Kleidungsstücken.
| Occasion| Eine Kombination kann für einen bestimmten Anlass sein.
| Season | Eine Kombination kann für eine Jahreszeit geeignet sein.
| User | Eine Kombination gehört einem Nutzer.
 */
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
//diese Klasse soll in der Datenbank abgebildet werden
@Table(name = "combinations")
public class Combination {

    @Id
    //ID der Kombination
    //| id | name |
    //| 1 | Uni-Outfit |
    //| 2 | Party-Outfit |
    //| 3 | Sommer-Outfit |
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(length = 1000)
    private String description;
    //Jede Kombination kann eine Beschreibung haben, muss aber nich-->nullable=false

    @Enumerated(EnumType.STRING)
    private Occasion occasion;

    @Enumerated(EnumType.STRING)
    private Season season;

    //ManyToOne-Beziehung zwischen Kombination und Nutzer
    //Pflichtfeld: Besitzer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private User owner;

    @ManyToMany
    @JoinTable(
            name = "combination_items",
            joinColumns = @JoinColumn(name = "combination_id"),
            inverseJoinColumns = @JoinColumn(name = "item_id")
    )
    private List<Item> items = new ArrayList<>();

    // Getter und Setter

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Occasion getOccasion() {
        return occasion;
    }

    public void setOccasion(Occasion occasion) {
        this.occasion = occasion;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}