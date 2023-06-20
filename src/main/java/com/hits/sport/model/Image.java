package com.hits.sport.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import java.util.UUID;

@Data
@Entity
public class Image {
    @Id
    private UUID id;
    private byte[] value;

    @PrePersist
    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }
}
