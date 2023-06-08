package com.hits.sport.repository;

import com.hits.sport.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends CrudRepository<User, UUID>, JpaSpecificationExecutor {

    Boolean existsByEmail(String email);

    Boolean existsByLogin(String login);

    Optional<User> findByLogin(String login);

    List<User> findAll();
}
