package com.hits.sport.model.template;

import com.hits.sport.model.Trainer;
import com.hits.sport.model.edited.EditedComplex;
import com.hits.sport.model.edited.EditedExercise;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class TrainingTemplate {
    @Id
    private UUID id;

    private String name;

    private String description;

    private Boolean published;

    private Boolean common;

    @ManyToOne
    private Trainer trainer;

    @OneToMany
    private List<EditedExercise> editedExercises;

    @OneToMany
    private List<EditedComplex> editedComplexes;

    @ManyToMany
    private Set<Trainer> allowed = new HashSet<>();

    @ManyToMany
    private Set<Trainer> liked = new HashSet<>();

    @PrePersist
    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }
}
