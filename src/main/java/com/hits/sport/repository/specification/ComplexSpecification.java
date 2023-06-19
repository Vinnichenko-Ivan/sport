package com.hits.sport.repository.specification;

import com.hits.sport.dto.complex.QueryComplexDto;
import com.hits.sport.model.template.ComplexTemplate;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ComplexSpecification implements Specification<ComplexTemplate> {
    private final QueryComplexDto queryComplexDto;

    public ComplexSpecification(QueryComplexDto queryComplexDto) {
        this.queryComplexDto = queryComplexDto;
    }

    @Override
    public Predicate toPredicate(Root<ComplexTemplate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and();
    }
}
