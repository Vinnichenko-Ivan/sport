package com.hits.sport.dto.complex;

import com.hits.sport.dto.common.PaginationQueryDto;
import lombok.Data;

@Data
public class QueryComplexDto {
    private String name;
    private Boolean common;
    private Boolean my;
    private Boolean shared;
    private Boolean published;
    private Boolean liked;
    PaginationQueryDto paginationQueryDto;
}
