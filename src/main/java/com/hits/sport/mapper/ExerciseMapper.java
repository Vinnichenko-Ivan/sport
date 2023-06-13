package com.hits.sport.mapper;

import com.hits.sport.dto.exercise.FullExerciseDto;
import com.hits.sport.dto.exercise.ShortExerciseDto;
import com.hits.sport.model.CreateExerciseDto;
import com.hits.sport.model.template.ExerciseTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "common", ignore = true)
    @Mapping(target = "trainer", ignore = true)
    ExerciseTemplate map(CreateExerciseDto createExerciseDto);

    @Mapping(target = "trainerId", source = "trainer.id")
    FullExerciseDto map(ExerciseTemplate exerciseTemplate);

    ShortExerciseDto mapToShort(ExerciseTemplate exerciseTemplate);
}
