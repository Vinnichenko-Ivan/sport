package com.hits.sport.mapper;

import com.hits.sport.dto.FullExerciseDto;
import com.hits.sport.dto.ShortExerciseDto;
import com.hits.sport.model.CreateExerciseDto;
import com.hits.sport.model.Exercise;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "common", ignore = true)
    @Mapping(target = "trainer", ignore = true)
    @Mapping(target = "allowedTrainer", ignore = true)
    Exercise map(CreateExerciseDto createExerciseDto);

    @Mapping(target = "allowedTrainerId", ignore = true)
    @Mapping(target = "trainerId", source = "trainer.id")
    FullExerciseDto map(Exercise exercise);

    ShortExerciseDto mapToShort(Exercise exercise);
}
