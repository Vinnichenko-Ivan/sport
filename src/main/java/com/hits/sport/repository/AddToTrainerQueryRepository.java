package com.hits.sport.repository;

import com.hits.sport.model.AddToTrainerQuery;
import com.hits.sport.model.Trainer;
import com.hits.sport.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface AddToTrainerQueryRepository extends CrudRepository<AddToTrainerQuery, UUID>, JpaSpecificationExecutor {
    Boolean existsByUserAndTrainer(User user, Trainer trainer);
}
