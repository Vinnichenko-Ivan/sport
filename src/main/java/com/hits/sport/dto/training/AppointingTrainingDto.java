package com.hits.sport.dto.training;

import com.hits.sport.dto.complex.ComplexEditForTemplateDto;
import com.hits.sport.dto.complex.EditedComplexCreateDto;
import com.hits.sport.dto.exercise.ExerciseForTemplateDto;
import com.hits.sport.model.edited.EditedExercise;
import lombok.Data;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
public class AppointingTrainingDto {
    private Set<UUID> groupIds;
    private Set<UUID> userIds;
    private Set<Date> dates;
    private List<ComplexEditForTemplateDto> complexes;
    private List<ExerciseForTemplateDto> exercise;
    private String name;
    private String description;
}
/**


 {
 "id": "fb58e99a-a4da-4f3a-9a9a-6ee954c637ef",
 "name": null,
 "description": "string",
 "dates":[]
 "exercises": [],
 "complexes": [
 {
 "complexId": "af9b1d61-735d-48b7-837a-fef00cc304fd",
 "exerciseValues": [
 {
 "exerciseId": "bd994981-6ae8-43e7-b9b9-26ff3e9e9ccf",
 "exerciseValues": {
 "repetitions": 0,
 "duration": 0,
 "weight": 0
 },
 "orderNumber": 1
 }
 ],
 "orderNumber": 1
 }
 ]
 }
 */
