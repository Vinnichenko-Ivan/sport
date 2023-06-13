package com.hits.sport.model.edited;

import com.hits.sport.model.ComplexType;
import lombok.Data;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class EditedComplex {
    @Id
    private UUID id;

    private Integer orderNumber;

    @Enumerated(EnumType.STRING)
    private ComplexType complexType;

    private Integer repetitions;
    private Integer spaceDuration;

    @OneToMany
    private List<EditedExercise> editedExercises;

    @PrePersist
    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }
}
