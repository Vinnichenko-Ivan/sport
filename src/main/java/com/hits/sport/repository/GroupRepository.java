package com.hits.sport.repository;

import com.hits.sport.model.Group;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface GroupRepository extends CrudRepository<Group, UUID> {
}
