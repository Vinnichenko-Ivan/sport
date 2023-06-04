package com.hits.sport.model;

import javax.persistence.*;
import lombok.Data;

import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "exercise")
public class Exercise {

    @Id
    private UUID id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "trainer_id")
    private Trainer trainer;

    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Trainer> allowedTrainer;

}
