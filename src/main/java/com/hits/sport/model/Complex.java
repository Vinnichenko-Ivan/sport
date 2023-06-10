package com.hits.sport.model;

import org.springframework.data.util.Pair;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
public class Complex {
    @Id
    private UUID id;

    private String name;

    private String description;

    private Boolean published;

    private Boolean common;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> allowedTrainer;

    @OneToMany(mappedBy = "complex")
    private List<SetExercise> exerciseValues;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> liked;

    @Enumerated(EnumType.STRING)
    private ComplexType complexType;

    private Integer repetitions;
    private Integer spaceDuration;

    private Boolean template;
    @PrePersist
    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }
}
