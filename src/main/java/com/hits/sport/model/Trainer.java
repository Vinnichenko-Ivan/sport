package com.hits.sport.model;

import javax.persistence.*;

import com.sun.istack.NotNull;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "trainer")
public class Trainer {

    @Id
    @Column(name = "id")
    private UUID id;

    @OneToOne
    @NotNull
    @PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
    private User user;

    @ManyToMany
    private Set<User> users;

    @OneToMany
    private Set<Group> groups;

    @ManyToMany
    private Set<Exercise> allowedExercise;

    @OneToMany
    private Set<Exercise> myExercise;

    private String shortName;
}
