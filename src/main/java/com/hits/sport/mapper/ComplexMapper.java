package com.hits.sport.mapper;

import com.hits.sport.dto.complex.ComplexCreateDto;
import com.hits.sport.dto.complex.EditComplexDto;
import com.hits.sport.dto.complex.FullComplexDto;
import com.hits.sport.dto.complex.ShortComplexDto;
import com.hits.sport.model.Complex;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ComplexMapper {
    Complex map(ComplexCreateDto complexCreateDto);

    ShortComplexDto mapToShort(Complex complex);
    FullComplexDto map(Complex complex);

    void map(@MappingTarget Complex complex, EditComplexDto editComplexDto);
}
