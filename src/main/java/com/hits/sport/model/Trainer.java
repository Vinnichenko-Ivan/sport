package com.hits.sport.model;

import javax.persistence.*;

import com.hits.sport.model.template.ExerciseTemplate;
import com.hits.sport.model.template.TrainingTemplate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SelectBeforeUpdate;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "trainer")
@NoArgsConstructor
public class Trainer {

    @Id
    @Column(name = "id")
    private UUID id;

    @OneToOne
    @PrimaryKeyJoinColumn(name="id", referencedColumnName="id")
    private User user;

    @ManyToMany(mappedBy = "trainers")
    private Set<User> users;

    @ManyToMany(mappedBy = "trainers")
    private Set<Group> groups = new HashSet<>();

    @OneToMany
    private Set<ExerciseTemplate> myExerciseTemplate;

    @OneToMany(mappedBy = "trainer")
    private Set<TrainingTemplate> trainingTemplates;

    private String shortName;

    public Trainer(User user) {
        this.user = user;
        this.id = user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Trainer trainer = (Trainer) o;
        return Objects.equals(id, trainer.id) && Objects.equals(user, trainer.user) && Objects.equals(users, trainer.users) && Objects.equals(groups, trainer.groups) && Objects.equals(myExerciseTemplate, trainer.myExerciseTemplate) && Objects.equals(trainingTemplates, trainer.trainingTemplates) && Objects.equals(shortName, trainer.shortName);
    }

    @Override
    public String toString() {
        return shortName;
    }
}
