package com.hits.sport.model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class EditedExercise {
    @Id
    private UUID id;

    @ManyToOne
    private SetExercise setExercise;

    @Embedded
    private ExerciseValues exerciseValues;

    @PrePersist
    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }
}
