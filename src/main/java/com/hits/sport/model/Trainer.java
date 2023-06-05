package com.hits.sport.model;

import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

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

    @ManyToMany
    private Set<User> users;

    @OneToMany
    private Set<Group> groups;

    @ManyToMany
    private Set<Exercise> allowedExercise;

    @OneToMany
    private Set<Exercise> myExercise;

    private String shortName;

    public Trainer(User user) {
        this.user = user;
        this.id = user.getId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
