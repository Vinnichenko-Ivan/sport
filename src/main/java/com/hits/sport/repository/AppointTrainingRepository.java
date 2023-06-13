package com.hits.sport.repository;

import com.hits.sport.model.appointed.AppointedTraining;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

public interface AppointTrainingRepository extends CrudRepository<AppointedTraining, UUID> {
    @Query(nativeQuery = true,
    value = "SELECT * FROM appointed_training;")
    List<AppointedTraining> getAll();
}
