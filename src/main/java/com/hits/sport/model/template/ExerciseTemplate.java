package com.hits.sport.model.template;

import javax.persistence.*;

import com.hits.sport.model.ExerciseValues;
import com.hits.sport.model.MuscleGroup;
import com.hits.sport.model.Trainer;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "exercise")
public class ExerciseTemplate {

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

    @Embedded
    private ExerciseValues defaultValues;

    private UUID imageId;

    @PrePersist
    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }
}
