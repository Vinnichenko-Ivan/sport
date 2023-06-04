package com.hits.sport.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(unique=true)
    private String login;
    @Column(unique=true)
    private String email;
    private String password;

    private Boolean confirm = false;

    private String name;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Trainer trainer;

    @ManyToMany
    private Set<Group> groups = new HashSet<>();

    @ManyToMany(mappedBy = "users")
    private Set<Trainer> trainers = new HashSet<>();

    @PrePersist
    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }

}
