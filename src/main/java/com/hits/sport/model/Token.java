package com.hits.sport.model;

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
}
