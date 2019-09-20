package com.space.service;

import com.space.model.Ship;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public interface ShipService {
    Ship getById(Long id);

    void save(Ship ship);

    void delete(Long id);

    List<Ship> getAll();

    Page<Ship> findAll(Specification<Ship> spec, Pageable pageable);

    List<Ship> findAll(Specification<Ship> spec);
}