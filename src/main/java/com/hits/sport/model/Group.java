package com.hits.sport.model;

import javax.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "groups")
public class Group {

    @Id
    private UUID id;
    private String description;

    private String name;

    @ManyToMany
    private Set<Trainer> trainers;

    @ManyToOne
    private Trainer mainTrainer;

    @ManyToMany
    private Set<User> users;

    private UUID imageId;
    @PrePersist
    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }
}
