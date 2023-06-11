package com.hits.sport.repository.specification;

import com.hits.sport.model.Complex;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ComplexSpecification implements Specification<Complex> {
    @Override
    public Predicate toPredicate(Root<Complex> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.or();
    }
}
