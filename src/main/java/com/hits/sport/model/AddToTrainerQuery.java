package com.hits.sport.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
}
