package com.hits.sport.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.UUID;

@Data
@Entity
public class Training {
    @Id
    private UUID id;

    private String name;

    private String description;

    private Boolean published;

    private Boolean common;

    @ManyToMany
    private List<Complex> complexes;

    private Boolean template;

    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }
}
