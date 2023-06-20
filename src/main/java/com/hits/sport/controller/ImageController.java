package com.hits.sport.controller;

import com.hits.sport.exception.NotFoundException;
import com.hits.sport.exception.NotImplementedException;
import com.hits.sport.model.Image;
import com.hits.sport.repository.ImageRepository;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class ImageController {
    private final ImageRepository imageRepository;

    @PostMapping("image")
    public UUID saveImage(@RequestBody byte[] value) {
        Image image = new Image();
        image.setValue(value);
        image = imageRepository.save(image);
        return image.getId();
    }

    @GetMapping("image/{id}")
    public byte[] getImage(@PathVariable UUID id) {
        return imageRepository.findById(id).orElseThrow(NotFoundException::new).getValue();
    }
}
