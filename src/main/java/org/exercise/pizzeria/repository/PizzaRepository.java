package org.exercise.pizzeria.repository;

import org.exercise.pizzeria.model.Pizza;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;

public interface PizzaRepository extends JpaRepository<Pizza, Integer> {

    public List<Pizza> findByNameContainingIgnoreCase(String name);
    public List<Pizza> findByDescriptionContainingAndPriceIsLessThanEqual(String description, BigDecimal price);

    public boolean existsByName(String name);

    public boolean existsByNameAndIdNot(String name, Integer id);
}
