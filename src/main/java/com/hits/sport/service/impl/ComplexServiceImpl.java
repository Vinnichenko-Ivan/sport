package com.hits.sport.service.impl;

import com.hits.sport.dto.common.PaginationAnswerDto;
import com.hits.sport.dto.common.PaginationQueryDto;
import com.hits.sport.dto.complex.*;
import com.hits.sport.exception.NotImplementedException;
import com.hits.sport.service.ComplexService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ComplexServiceImpl implements ComplexService {
    @Override
    public void createComplex(ComplexCreateDto complexCreateDto) {
        throw new NotImplementedException();
    }

    @Override
    public FullComplexDto getComplex(UUID complexId) {
        throw new NotImplementedException();
    }

    @Override
    public PaginationAnswerDto<ShortComplexDto> getComplexes(QueryComplexDto queryComplexDto, PaginationQueryDto paginationQueryDto) {
        throw new NotImplementedException();
    }

    @Override
    public void editComplex(UUID complexId, EditComplexDto editComplexDto) {
        throw new NotImplementedException();
    }
}

