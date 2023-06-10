package com.hits.sport.dto.complex;

import com.hits.sport.model.ComplexType;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
public class ShortComplexDto {
    private UUID id;
    private String name;
    private Boolean common;
    private ComplexType complexType;
}
