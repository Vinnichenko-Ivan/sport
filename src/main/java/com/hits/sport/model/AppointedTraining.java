package com.hits.sport.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class AppointedTraining {
    @Id
    private UUID id;

    @ManyToOne
    private Training training;

    @ManyToMany
    private Set<User> user;

    @ManyToMany
    private Set<Group> group;

    @ElementCollection
    private Set<Date> dates;

    @OneToMany
    private Set<EditedComplex> editedComplexes;

    @PrePersist
    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }
}
