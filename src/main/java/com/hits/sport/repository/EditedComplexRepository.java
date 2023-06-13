package com.hits.sport.repository;

import com.hits.sport.model.edited.EditedComplex;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface EditedComplexRepository extends CrudRepository<EditedComplex, UUID> {
}
