package com.hits.sport.dto.sharing;

import com.hits.sport.dto.complex.ShortComplexDto;
import com.hits.sport.dto.exercise.ShortExerciseDto;
import com.hits.sport.dto.trainer.ShortTrainerDto;
import com.hits.sport.dto.training.ShortTrainingDto;
import com.hits.sport.model.template.ExerciseTemplate;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Data
public class SharingTokensDto {
    private UUID id;
    private Date expDate;
    private List<ShortExerciseDto> exercises;
    private List<ShortComplexDto> complexes;
    private List<ShortTrainingDto> trainings;
    private Integer maxCount;
}
