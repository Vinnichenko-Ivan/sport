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

    private String name;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups")
    private Set<Trainer> trainers;

    @ManyToOne
    @JoinColumn(name = "main_trainer_id")
    private Trainer mainTrainer;

    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "groups")
    private Set<User> users;

    @PrePersist
    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }
}
