package org.exercise.pizzeria.repository;

import org.exercise.pizzeria.model.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    public boolean existsByName(String name);
}
