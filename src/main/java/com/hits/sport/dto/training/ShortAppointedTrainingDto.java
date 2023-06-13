package com.hits.sport.dto.training;

import lombok.Data;

import java.util.Date;
import java.util.Set;
import java.util.UUID;

@Data
public class ShortAppointedTrainingDto {
    private UUID id;
    private Set<Date> dates;
    private String name;
    private String trainerName;
}
