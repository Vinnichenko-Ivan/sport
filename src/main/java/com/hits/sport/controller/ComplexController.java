package com.hits.sport.controller;

import com.hits.sport.dto.common.PaginationAnswerDto;
import com.hits.sport.dto.common.PaginationQueryDto;
import com.hits.sport.dto.complex.*;
import com.hits.sport.service.ComplexService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

import static com.hits.sport.utils.Path.*;

@Api
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
@Validated
public class ComplexController {
    private final ComplexService complexService;

    @PostMapping(COMPLEX_URL)
    void createComplex(@Valid @RequestBody  ComplexCreateDto complexCreateDto) {
        complexService.createComplex(complexCreateDto);
    }

    @GetMapping(THE_COMPLEX_URL)
    FullComplexDto getComplex(@PathVariable UUID complexId) {
        return complexService.getComplex(complexId);
    }

    @PostMapping(COMPLEXES_URL)
    PaginationAnswerDto<ShortComplexDto> getComplexes(@Valid @RequestBody QueryComplexDto queryComplexDto) {
        return complexService.getComplexes(queryComplexDto, queryComplexDto.getPaginationQueryDto());
    }

    @PutMapping(THE_COMPLEX_URL)
    void editComplex(@PathVariable UUID complexId, @Valid @RequestBody EditComplexDto editComplexDto) {
        complexService.editComplex(complexId, editComplexDto);
    }
}
