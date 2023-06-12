package com.hits.sport.model;

import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class EditedComplex {
    @Id
    private UUID id;

    @ManyToOne
    private Complex complex;

    @OneToMany
    private List<EditedExercise> editedExercise;


    @PrePersist
    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }
}
