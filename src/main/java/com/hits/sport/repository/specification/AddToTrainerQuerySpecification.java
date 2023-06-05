package com.hits.sport.repository.specification;

import com.hits.sport.model.*;
import com.hits.sport.utils.Utils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class AddToTrainerQuerySpecification implements Specification<AddToTrainerQuery> {
    private final String shortName;

    public AddToTrainerQuerySpecification(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public Predicate toPredicate(Root<AddToTrainerQuery> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(root.get(AddToTrainerQuery_.user).get(User_.name), Utils.toSqlParam(shortName));
    }
}
