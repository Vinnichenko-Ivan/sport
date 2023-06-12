package com.hits.sport.dto.training;

import com.hits.sport.dto.complex.FullComplexDto;
import com.hits.sport.dto.complex.FullSetComplexDto;
import com.hits.sport.model.Complex;
import lombok.Data;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.List;
import java.util.UUID;

@Data
public class FullTrainingDto {
    private UUID id;

    private String name;

    private String description;

    private Boolean published;

    private Boolean common;

    private List<FullSetComplexDto> complexes;

    private Boolean template;
}
