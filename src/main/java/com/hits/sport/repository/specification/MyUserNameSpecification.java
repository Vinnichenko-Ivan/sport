package com.hits.sport.repository.specification;

import com.hits.sport.model.*;
import com.hits.sport.utils.Utils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class MyUserNameSpecification implements Specification<User> {
    private final Trainer trainer;
    private final String name;

    public MyUserNameSpecification(Trainer trainer, String name) {
        this.trainer = trainer;
        this.name = name;
    }

    @Override
    public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return criteriaBuilder.and(
                criteriaBuilder.like(root.get(User_.name), Utils.toSqlParam(name)),
                criteriaBuilder.isMember(trainer, root.get(User_.trainers))
        );
    }
}
