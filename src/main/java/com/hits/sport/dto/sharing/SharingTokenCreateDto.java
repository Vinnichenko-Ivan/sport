package com.hits.sport.dto.sharing;

import com.hits.sport.model.template.ComplexTemplate;
import com.hits.sport.model.template.ExerciseTemplate;
import com.hits.sport.model.template.TrainingTemplate;
import lombok.Data;

import javax.persistence.ManyToMany;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class SharingTokenCreateDto {
    private Date expDate;
    private List<UUID> exercises;
    private List<UUID> complexes;
    private List<UUID> trainings;
    private Integer maxCount;
}
