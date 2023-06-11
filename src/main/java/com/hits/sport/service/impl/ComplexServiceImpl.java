package com.hits.sport.service.impl;

import com.hits.sport.dto.common.PaginationAnswerDto;
import com.hits.sport.dto.common.PaginationQueryDto;
import com.hits.sport.dto.complex.*;
import com.hits.sport.exception.NotFoundException;
import com.hits.sport.exception.NotImplementedException;
import com.hits.sport.mapper.ComplexMapper;
import com.hits.sport.model.Complex;
import com.hits.sport.repository.ComplexRepository;
import com.hits.sport.repository.specification.ComplexSpecification;
import com.hits.sport.service.ComplexService;
import com.hits.sport.service.ExerciseService;
import com.hits.sport.utils.JwtProvider;
import com.hits.sport.utils.Utils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplexServiceImpl implements ComplexService {
    private final ComplexMapper complexMapper;
    private final ComplexRepository complexRepository;
    private final ExerciseService exerciseService;
    private final JwtProvider jwtProvider;
    @Override
    public void createComplex(ComplexCreateDto complexCreateDto) {
        exerciseService.checkTrainer(jwtProvider.getUser().getTrainer());
        Complex complex = complexMapper.map(complexCreateDto);
        complex.setTrainer(jwtProvider.getUser().getTrainer());
        complex.setCommon(false);
        complex.setAllowedTrainer(new HashSet<>());
        complexRepository.save(complex);
    }

    @Override
    public FullComplexDto getComplex(UUID complexId) {
        exerciseService.checkTrainer(jwtProvider.getUser().getTrainer());
        Complex complex = complexRepository.findById(complexId).orElseThrow(() -> new NotFoundException("complex not found"));
        return complexMapper.map(complex);
        //throw new NotImplementedException();// TODO проверка доступа
    }

    @Override
    public PaginationAnswerDto<ShortComplexDto> getComplexes(QueryComplexDto queryComplexDto, PaginationQueryDto paginationQueryDto) {
        exerciseService.checkTrainer(jwtProvider.getUser().getTrainer());
        Page<Complex> page = complexRepository.findAll(new ComplexSpecification(), Utils.toPageable(paginationQueryDto));
        PaginationAnswerDto<ShortComplexDto> dto = Utils.toAnswer(page, (complex) -> {
            ShortComplexDto complexDto = complexMapper.mapToShort(complex);
            return complexDto;
        });
        return dto;
    }

    @Override
    public void editComplex(UUID complexId, EditComplexDto editComplexDto) {
        exerciseService.checkTrainer(jwtProvider.getUser().getTrainer());
        Complex complex = complexRepository.findById(complexId).orElseThrow(() -> new NotFoundException("complex not found"));
        complexMapper.map(complex,editComplexDto);
        complexRepository.save(complex);
    }
}

