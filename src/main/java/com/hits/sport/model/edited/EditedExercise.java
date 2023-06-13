package com.hits.sport.model.edited;

import com.hits.sport.model.ExerciseValues;
import com.hits.sport.model.template.ExerciseTemplate;
import lombok.Data;

import javax.persistence.*;
import java.util.UUID;

@Data
@Entity
@Table(name="edited_exercise")
public class EditedExercise {
    @Id
    private UUID id;
    @ManyToOne
    private ExerciseTemplate exercise;
    @Embedded
    private ExerciseValues exerciseValues;

    private Integer orderNumber;

    @PrePersist
    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }
}
