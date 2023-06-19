package com.hits.sport.mapper;

import com.hits.sport.dto.exercise.EditExerciseDto;
import com.hits.sport.dto.exercise.FullExerciseDto;
import com.hits.sport.dto.exercise.ShortExerciseDto;
import com.hits.sport.dto.CreateExerciseDto;
import com.hits.sport.model.template.ExerciseTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ExerciseMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "common", ignore = true)
    @Mapping(target = "trainer", ignore = true)
    ExerciseTemplate map(CreateExerciseDto createExerciseDto);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "common", ignore = true)
    @Mapping(target = "trainer", ignore = true)
    void map(@MappingTarget ExerciseTemplate exerciseTemplate, EditExerciseDto editExerciseDto);

    @Mapping(target = "trainerId", source = "trainer.id")
    FullExerciseDto map(ExerciseTemplate exerciseTemplate);

    @Mapping(target = "muscleGroup", source = "muscleGroups")
    ShortExerciseDto mapToShort(ExerciseTemplate exerciseTemplate);
}
