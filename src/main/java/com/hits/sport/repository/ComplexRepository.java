package com.hits.sport.repository;

import com.hits.sport.model.Complex;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ComplexRepository extends CrudRepository<Complex, UUID>, JpaSpecificationExecutor {
}
