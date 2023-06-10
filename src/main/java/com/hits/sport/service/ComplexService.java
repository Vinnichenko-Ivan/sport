package com.hits.sport.service;

import com.hits.sport.dto.common.PaginationAnswerDto;
import com.hits.sport.dto.common.PaginationQueryDto;
import com.hits.sport.dto.complex.*;

import java.util.UUID;

public interface ComplexService {
    void createComplex(ComplexCreateDto complexCreateDto);

    FullComplexDto getComplex(UUID complexId);

    PaginationAnswerDto<ShortComplexDto> getComplexes(QueryComplexDto queryComplexDto, PaginationQueryDto paginationQueryDto);

    void editComplex(UUID complexId, EditComplexDto editComplexDto);
}
