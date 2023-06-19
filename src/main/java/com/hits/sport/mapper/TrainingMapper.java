package com.hits.sport.mapper;

import com.hits.sport.dto.training.ShortTrainingDto;
import com.hits.sport.dto.training.TrainingCreateDto;
import com.hits.sport.model.template.TrainingTemplate;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TrainingMapper {
    TrainingTemplate map(TrainingCreateDto trainingCreateDto);

    ShortTrainingDto mapToShort(TrainingTemplate trainingTemplate);
}
