package com.hits.sport.controller;

import com.hits.sport.dto.sharing.SharingTokenCreateDto;
import com.hits.sport.dto.sharing.SharingTokensDto;
import com.hits.sport.exception.NotImplementedException;
import com.hits.sport.service.SharingService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class SharingController {
    private final SharingService sharingService;
    @PostMapping("sharing/create")
    public String createToken(@Validated @RequestBody SharingTokenCreateDto sharingTokenCreateDto) {
        return sharingService.createToken(sharingTokenCreateDto);
    }

    @PostMapping("sharing/use")
    public void useToken(String value) {
        sharingService.useToken(value);
    }

    @GetMapping("sharing")
    public List<SharingTokensDto> getMy() {
        return sharingService.getMy();
    }

    @DeleteMapping("sharing/{id}")
    public void deleteToken(@PathVariable UUID id) {
        sharingService.deleteToken(id);
    }
}
