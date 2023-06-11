package com.hits.sport.repository.specification;

import com.hits.sport.model.Trainer;
import com.hits.sport.model.Trainer_;
import com.hits.sport.utils.Utils;
import jdk.jshell.execution.Util;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class TrainerShortNameSpecification implements Specification<Trainer> {
    private final String shortName;

    public TrainerShortNameSpecification(String shortName) {
        this.shortName = shortName;
    }

    @Override
    public Predicate toPredicate(Root<Trainer> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.like(criteriaBuilder.lower(root.get(Trainer_.shortName)), Utils.toSqlParam(shortName));
    }
}
