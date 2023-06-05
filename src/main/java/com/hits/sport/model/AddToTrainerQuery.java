package com.hits.sport.model;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class AddToTrainerQuery {
    @Id
    private UUID id;

    @ManyToOne
    private Trainer trainer;

    @ManyToOne
    private User user;

    private Date createdDate;

    @PrePersist
    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
        this.createdDate = new Date(System.currentTimeMillis());
    }

}
