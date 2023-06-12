package com.hits.sport.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.UUID;

@Entity
@Data//TODO удалить т к заменен
public class SetTraining {
    @Id
    private UUID id;

    @ManyToOne
    private User user;

    @ManyToOne
    private Training training;

    //система индивидуальных изменений.

    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }
}
