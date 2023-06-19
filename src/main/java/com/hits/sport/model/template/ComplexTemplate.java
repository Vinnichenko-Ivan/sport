package com.hits.sport.model.template;

import com.hits.sport.model.ComplexType;
import com.hits.sport.model.Trainer;
import com.hits.sport.model.edited.EditedExercise;
import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Data
public class ComplexTemplate {
    @Id
    private UUID id;

    private String name;

    private String description;

    private Boolean published;

    private Boolean common;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @Enumerated(EnumType.STRING)
    private ComplexType complexType;

    private Integer repetitions;
    private Integer spaceDuration;

    @OneToMany
    private List<EditedExercise> editedExercises;

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
