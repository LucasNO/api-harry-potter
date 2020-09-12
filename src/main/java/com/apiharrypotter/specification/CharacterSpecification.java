package com.apiharrypotter.specification;

import com.apiharrypotter.entity.Character;
import com.apiharrypotter.filter.CharacterFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

public class CharacterSpecification implements Specification<Character> {

    private static final String HOUSE = "house";
    private static final String NAME = "name";

    private final CharacterFilter filter;

    public CharacterSpecification(CharacterFilter filter) {
        this.filter = filter;
    }

    @Override
    public Predicate toPredicate(Root<Character> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {

        final Path<String> house = root.get(HOUSE);
        final Path<String> name = root.get(NAME);

        final List<Predicate> predicates = new ArrayList<>();

        String houseFilter = filter.getHouse();
        if (!ObjectUtils.isEmpty(houseFilter)){
            predicates.add(criteriaBuilder.equal(house, houseFilter));
        }

        criteriaQuery.orderBy(criteriaBuilder.asc(name));

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}
