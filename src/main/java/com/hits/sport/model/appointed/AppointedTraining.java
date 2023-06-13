package com.hits.sport.model.appointed;

import com.hits.sport.model.Group;
import com.hits.sport.model.User;
import com.hits.sport.model.edited.EditedComplex;
import com.hits.sport.model.edited.EditedExercise;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
public class AppointedTraining {
    @Id
    private UUID id;

    @ElementCollection
    private Set<Date> dates;

    @OneToMany
    private Set<User> users;
    @OneToMany
    private Set<Group> groups;

    @OneToMany
    private List<EditedComplex> editedComplexes;

    @OneToMany
    private List<EditedExercise> editedExercises;

    @PrePersist
    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }
}
