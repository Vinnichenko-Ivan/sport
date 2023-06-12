package com.hits.sport.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class SetComplex {
    @Id
    private UUID id;

    @ManyToOne
    private Complex complex;

    @OneToMany
    private List<SetExercise> exerciseValues;

    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }
}
