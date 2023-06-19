package com.hits.sport.model.token;

import com.hits.sport.model.Trainer;
import com.hits.sport.model.template.ComplexTemplate;
import com.hits.sport.model.template.ExerciseTemplate;
import com.hits.sport.model.template.TrainingTemplate;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class SharingToken {
    @Id
    private UUID id;

    private Date expDate;

    @ManyToMany
    private List<ExerciseTemplate> exercises;
    @ManyToMany
    private List<ComplexTemplate> complexes;
    @ManyToMany
    private List<TrainingTemplate> trainings;

    private Integer maxCount;

    private String value;

    @ManyToOne
    private Trainer trainer;

    @PrePersist
    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }
}
