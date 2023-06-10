package com.hits.sport.utils;

import com.hits.sport.dto.common.PaginationAnswerDto;
import com.hits.sport.dto.common.PaginationQueryDto;
import com.hits.sport.exception.NotFoundException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    public static String toSqlParam(String arg) {
        if(arg == null)
        {
            arg = "";
        }
        return "%" + arg.toLowerCase() + "%";
    }

    public static Pageable toPageable(PaginationQueryDto dto, List<Sort.Order> orders) {
        int page = dto.getPage() - 1;
        int size = dto.getSize();
        if(page < 0) {
            throw new NotFoundException("index <= 0");
        }
        if(size <= 0) {
            throw new NotFoundException("size <= 0");
        }
        return PageRequest.of(page, size, Sort.by(orders));
    }

    public static Pageable toPageable(PaginationQueryDto dto) {
        int page = dto.getPage() - 1;
        int size = dto.getSize();
        if(page < 0) {
            throw new NotFoundException("index <= 0");
        }
        if(size <= 0) {
            throw new NotFoundException("size <= 0");
        }
        return PageRequest.of(page, size);
    }

    public static <T> PaginationAnswerDto<T> toAnswer(Page<T> page) {
        PaginationAnswerDto<T> paginationAnswerDto = new PaginationAnswerDto<T>();
        paginationAnswerDto.setData(page.getContent());
        paginationAnswerDto.setPage(page.getNumber());
        paginationAnswerDto.setMaxPage(page.getTotalPages());
        paginationAnswerDto.setSize(page.getSize());
        return paginationAnswerDto;
    }



    public static <T, K> PaginationAnswerDto<T> toAnswer(Page<K> page, Converter<K, T> converter) {
        PaginationAnswerDto<T> paginationAnswerDto = new PaginationAnswerDto<T>();
        paginationAnswerDto.setData(page.getContent().stream().map(converter::convert).collect(Collectors.toList()));
        paginationAnswerDto.setPage(page.getNumber());
        paginationAnswerDto.setMaxPage(page.getTotalPages());
        paginationAnswerDto.setSize(page.getSize());
        return paginationAnswerDto;
    }

    public static <T, K> PaginationAnswerDto<T> toAnswerWData(Page<K> page) {
        PaginationAnswerDto<T> paginationAnswerDto = new PaginationAnswerDto<T>();
        paginationAnswerDto.setPage(page.getNumber());
        paginationAnswerDto.setMaxPage(page.getTotalPages());
        paginationAnswerDto.setSize(page.getSize());
        return paginationAnswerDto;
    }
}
