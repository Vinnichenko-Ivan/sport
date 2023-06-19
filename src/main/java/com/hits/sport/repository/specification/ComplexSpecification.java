package com.hits.sport.repository.specification;

import com.hits.sport.dto.complex.QueryComplexDto;
import com.hits.sport.model.Trainer;
import com.hits.sport.model.template.ComplexTemplate;
import com.hits.sport.model.template.ComplexTemplate_;
import com.hits.sport.utils.Utils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ComplexSpecification implements Specification<ComplexTemplate> {
    private final QueryComplexDto queryComplexDto;
    private final Trainer trainer;

    public ComplexSpecification(QueryComplexDto queryComplexDto, Trainer trainer) {
        this.queryComplexDto = queryComplexDto;
        this.trainer = trainer;
    }

    @Override
    public Predicate toPredicate(Root<ComplexTemplate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate base = criteriaBuilder.like(criteriaBuilder.lower(root.get(ComplexTemplate_.name)), Utils.toSqlParam(queryComplexDto.getName()));
        Predicate type = criteriaBuilder.or(
                queryComplexDto.getCommon()?criteriaBuilder.equal(root.get(ComplexTemplate_.common), true):criteriaBuilder.or(),//TODO shared and liked
                queryComplexDto.getPublished()?criteriaBuilder.equal(root.get(ComplexTemplate_.published), true):criteriaBuilder.or(),
                queryComplexDto.getMy()&&trainer != null?criteriaBuilder.equal(root.get(ComplexTemplate_.trainer), trainer):criteriaBuilder.or(),
                queryComplexDto.getLiked()&&trainer != null?criteriaBuilder.isMember(trainer, root.get(ComplexTemplate_.liked)):criteriaBuilder.or(),
                queryComplexDto.getShared()&&trainer != null?criteriaBuilder.isMember(trainer, root.get(ComplexTemplate_.allowed)):criteriaBuilder.or()
        );
        base = criteriaBuilder.and(
                base,
                type
        );
        return base;
    }
}
