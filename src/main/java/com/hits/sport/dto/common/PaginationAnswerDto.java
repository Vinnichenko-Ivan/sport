package com.hits.sport.dto.common;

import lombok.Data;

import java.util.List;

@Data
public class PaginationAnswerDto<T> {
    private int page;
    private int maxPage;
    private int size;
    private List<T> data;
}
