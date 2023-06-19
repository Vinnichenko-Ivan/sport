package com.hits.sport.repository.specification;

import com.hits.sport.dto.training.QueryTrainingDto;
import com.hits.sport.model.Trainer;
import com.hits.sport.model.template.ComplexTemplate;
import com.hits.sport.model.template.ComplexTemplate_;
import com.hits.sport.model.template.TrainingTemplate;
import com.hits.sport.model.template.TrainingTemplate_;
import com.hits.sport.utils.Utils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class TrainingSpecification implements Specification<TrainingTemplate> {
    private final QueryTrainingDto queryTrainingDto;
    private final Trainer trainer;

    public TrainingSpecification(QueryTrainingDto queryTrainingDto, Trainer trainer) {
        this.queryTrainingDto = queryTrainingDto;
        this.trainer = trainer;
    }

    @Override
    public Predicate toPredicate(Root<TrainingTemplate> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate base = criteriaBuilder.like(criteriaBuilder.lower(root.get(TrainingTemplate_.name)), Utils.toSqlParam(queryTrainingDto.getName()));
        Predicate type = criteriaBuilder.or(
                queryTrainingDto.getCommon()?criteriaBuilder.equal(root.get(TrainingTemplate_.common), true):criteriaBuilder.or(),//TODO shared and liked
                queryTrainingDto.getPublished()?criteriaBuilder.equal(root.get(TrainingTemplate_.published), true):criteriaBuilder.or(),
                queryTrainingDto.getMy()&&trainer != null?criteriaBuilder.equal(root.get(TrainingTemplate_.trainer), trainer):criteriaBuilder.or(),
                queryTrainingDto.getLiked()&&trainer != null?criteriaBuilder.isMember(trainer, root.get(TrainingTemplate_.liked)):criteriaBuilder.or(),
                queryTrainingDto.getShared()&&trainer != null?criteriaBuilder.isMember(trainer, root.get(TrainingTemplate_.allowed)):criteriaBuilder.or()
        );
        base = criteriaBuilder.and(
                base,
                type
        );
        return base;
    }
}
