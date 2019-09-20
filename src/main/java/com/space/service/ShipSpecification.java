package com.space.service;

import com.space.model.Ship;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShipSpecification implements Specification<Ship> {
    private final ShipServiceImplementation.ShipFilters shipFilters;

    public ShipSpecification(ShipServiceImplementation.ShipFilters shipFilters) {

        this.shipFilters = shipFilters;
    }

    @Override
    public Predicate toPredicate(Root<Ship> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<Predicate>();
        if (shipFilters.getName() != null) {
            predicates.add(criteriaBuilder.like(root.get("name"), "%" + shipFilters.getName() + "%"));
        }
        if (shipFilters.getPlanet() != null) {
            predicates.add(criteriaBuilder.like(root.get("planet"), "%" + shipFilters.getPlanet() + "%"));
        }
        if (shipFilters.getShipType() != null) {
            predicates.add(criteriaBuilder.equal(root.get("shipType"), shipFilters.getShipType()));
        }
        if (shipFilters.getAfter() != null) {
            predicates.add(criteriaBuilder.greaterThan(root.get("prodDate"), new Date(shipFilters.getAfter())));
        }
        if (shipFilters.getBefore() != null) {
            predicates.add(criteriaBuilder.lessThan(root.get("prodDate"), new Date(shipFilters.getBefore())));
        }
        if (shipFilters.getIsUsed() != null) {
            predicates.add(criteriaBuilder.equal(root.get("isUsed"), shipFilters.getIsUsed()));
        }
        if (shipFilters.getMinCrewSize() != null) {
            predicates.add(criteriaBuilder.ge(root.get("crewSize"), shipFilters.getMinCrewSize()));
        }
        if (shipFilters.getMaxCrewSize() != null) {
            predicates.add(criteriaBuilder.le(root.get("crewSize"), shipFilters.getMaxCrewSize()));
        }
        if (shipFilters.getMinSpeed() != null) {
            predicates.add(criteriaBuilder.ge(root.get("speed"), shipFilters.getMinSpeed()));
        }
        if (shipFilters.getMaxSpeed() != null) {
            predicates.add(criteriaBuilder.le(root.get("speed"), shipFilters.getMaxSpeed()));
        }
        if (shipFilters.getMinRating() != null) {
            predicates.add(criteriaBuilder.ge(root.get("rating"), shipFilters.getMinRating()));
        }
        if (shipFilters.getMaxRating() != null) {
            predicates.add(criteriaBuilder.le(root.get("rating"), shipFilters.getMaxRating()));
        }
        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}