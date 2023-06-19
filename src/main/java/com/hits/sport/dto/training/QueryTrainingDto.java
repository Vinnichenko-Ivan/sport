package com.hits.sport.dto.training;

import com.hits.sport.dto.common.PaginationQueryDto;
import lombok.Data;

@Data
public class QueryTrainingDto {
    private String name;
    private Boolean common;
    private Boolean my;
    private Boolean shared;
    private Boolean published;
    private Boolean liked;
    private PaginationQueryDto paginationQueryDto;
}
