package com.hits.sport.model.token;

import com.hits.sport.model.User;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class Token {
    @Id
    private UUID id;

    @ManyToOne
    private User user;

    private String token;

    @Enumerated(EnumType.STRING)
    private TokenType type;

    private Date expDate;

    @PrePersist
    public void generate()
    {
        this.id = java.util.UUID.randomUUID();
    }
}
