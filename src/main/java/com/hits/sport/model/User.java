package com.hits.sport.model;

import lombok.Data;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(unique=true)
    private String login;
    @Column(unique=true)
    private String email;
    private String password;

    private Boolean confirm = false;

    private String name;

    @OneToOne(cascade = CascadeType.ALL, mappedBy = "user")
    private Trainer trainer;

    @ManyToMany(mappedBy = "users")
    private Set<Group> groups = new HashSet<>();

    @ManyToMany
    private Set<Trainer> trainers = new HashSet<>();

    @PrePersist
    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(login, user.login) && Objects.equals(email, user.email) && Objects.equals(password, user.password) && Objects.equals(confirm, user.confirm) && Objects.equals(name, user.name) && Objects.equals(trainer, user.trainer) && Objects.equals(groups, user.groups) && Objects.equals(trainers, user.trainers);
    }

    @Override
    public String toString() {
        return name;
    }
}
