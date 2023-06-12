package com.hits.sport.dto.training;

import com.hits.sport.dto.complex.EditedComplexCreateDto;
import com.hits.sport.model.Complex;
import lombok.Data;

import javax.persistence.ManyToMany;
import java.util.List;
import java.util.UUID;

@Data
public class TrainingCreateDto {
    private String name;

    private String description;

    private Boolean published;

    private Boolean common;

    private List<EditedComplexCreateDto> editedComplexes;

    private Boolean template;
}
