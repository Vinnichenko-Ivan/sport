package com.hits.sport.repository;

import com.hits.sport.model.Trainer;
import com.hits.sport.model.token.SharingToken;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SharedTokenRepository extends CrudRepository<SharingToken, UUID> {
    Optional<SharingToken> getByValue(String value);

    List<SharingToken> getByTrainer(Trainer trainer);
}
