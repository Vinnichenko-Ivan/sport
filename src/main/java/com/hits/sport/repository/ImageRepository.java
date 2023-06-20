package com.hits.sport.repository;

import com.hits.sport.model.Image;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ImageRepository extends CrudRepository<Image, UUID> {
}
