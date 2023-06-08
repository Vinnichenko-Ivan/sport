package com.hits.sport.model;

import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
public class SetExercise {
    @Id
    private UUID id;

    @ManyToOne
    private Exercise exercise;

    @Embedded
    private ExerciseValues exerciseValues;

    @ManyToOne
    private Complex complex;

    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }
}
