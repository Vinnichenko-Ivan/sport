package com.hits.sport.mapper;

import com.hits.sport.dto.complex.ComplexCreateDto;
import com.hits.sport.dto.complex.EditComplexDto;
import com.hits.sport.dto.complex.FullComplexDto;
import com.hits.sport.dto.complex.ShortComplexDto;
import com.hits.sport.model.template.ComplexTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ComplexMapper {
    ComplexTemplate map(ComplexCreateDto complexCreateDto);

    ShortComplexDto mapToShort(ComplexTemplate complexTemplate);
    FullComplexDto map(ComplexTemplate complexTemplate);

    void map(@MappingTarget ComplexTemplate complexTemplate, EditComplexDto editComplexDto);
}
