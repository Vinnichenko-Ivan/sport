package com.hits.sport.service;

import com.hits.sport.exception.NotImplementedException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.UUID;

@Service
public interface LikedService {
     Boolean checkExercise(UUID id);
     Boolean checkComplex(UUID id);
     Boolean checkTraining(UUID id);

     void likeExercise(UUID id);
     void likeComplex(UUID id);
     void likeTraining(UUID id);

     void dislikeExercise(UUID id);
     void dislikeComplex(UUID id);
     void dislikeTraining(UUID id);
}
