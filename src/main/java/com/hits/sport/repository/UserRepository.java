package com.hits.sport.repository;

import com.hits.sport.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends CrudRepository<User, UUID> {

    Boolean existsByEmail(String email);

    Boolean existsByLogin(String login);

    Optional<User> findByLogin(String login);
}
