package com.hits.sport.model;

import javax.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "exercise")
public class Exercise {

    @Id
    private UUID id;

    private String name;

    private String description;

    private Boolean published;

    private Boolean common;

    @Enumerated(EnumType.STRING)
    @ElementCollection
    private Set<MuscleGroup> muscleGroups;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<User> allowedTrainer;

    @Embedded
    private ExerciseValues defaultValues;

    private UUID imageId;

    @PrePersist
    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }
}
