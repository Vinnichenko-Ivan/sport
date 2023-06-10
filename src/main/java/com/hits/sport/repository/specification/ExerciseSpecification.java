package com.hits.sport.repository.specification;

import com.hits.sport.dto.GetExerciseDto;
import com.hits.sport.model.Exercise;
import com.hits.sport.model.Exercise_;
import com.hits.sport.model.Trainer;
import com.hits.sport.utils.Utils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

public class ExerciseSpecification implements Specification<Exercise> {
    private final GetExerciseDto getExerciseDto;
    private final Trainer trainer;

    public ExerciseSpecification(GetExerciseDto getExerciseDto, Trainer trainer) {
        this.getExerciseDto = getExerciseDto;
        this.trainer = trainer;
    }

    private Predicate toPredict(Root<Exercise> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate def = criteriaBuilder.or();
        for(var group : getExerciseDto.getMuscleGroups()) {
            def = criteriaBuilder.or(
                    def,
                    criteriaBuilder.equal(root.get(Exercise_.muscleGroups), group)
            );
        }
        return def;
    }
    @Override
    public Predicate toPredicate(Root<Exercise> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        Predicate base = criteriaBuilder.like(criteriaBuilder.lower(root.get(Exercise_.name)), Utils.toSqlParam(getExerciseDto.getName()));
        if(getExerciseDto.getMuscleGroups() != null) {
            base = criteriaBuilder.and(
                    base,
                    toPredict(root, query, criteriaBuilder)
            );
        }
        Predicate type = criteriaBuilder.or(
                getExerciseDto.getCommon()?criteriaBuilder.equal(root.get(Exercise_.common), true):criteriaBuilder.or(),
                getExerciseDto.getShared()?criteriaBuilder.equal(root.get(Exercise_.published), true):criteriaBuilder.or(),//TODO shared
                getExerciseDto.getMy()&&trainer != null?criteriaBuilder.equal(root.get(Exercise_.trainer), trainer):criteriaBuilder.or()
        );
        base = criteriaBuilder.and(
                base,
                type
        );
        return base;
    }
}
