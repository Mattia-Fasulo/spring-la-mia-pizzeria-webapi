package org.exercise.pizzeria.services;

import org.exercise.pizzeria.exceptions.IngredientNotFoundException;
import org.exercise.pizzeria.model.Ingredient;
import org.exercise.pizzeria.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {
    @Autowired
    private IngredientRepository ingredientRepository;

    public Ingredient getById(Integer id) throws IngredientNotFoundException {
        Optional<Ingredient> result = ingredientRepository.findById(id);
         if(result.isEmpty()){
             throw new IngredientNotFoundException("Ingredient not found");
         } else {
             return result.get();
         }
    }

    public List<Ingredient> getAll(){
        return ingredientRepository.findAll(Sort.by("name"));
    }

    public Ingredient create(Ingredient formIngredient){
        Ingredient ingredientToPersist = new Ingredient();
        ingredientToPersist.setName(formIngredient.getName());
        return ingredientRepository.save(ingredientToPersist);
    }

    public Ingredient update(Ingredient formIngredient, Integer id){
        Ingredient ingredientToUpdate = getById(id);
        ingredientToUpdate.setName(formIngredient.getName());
        return  ingredientRepository.save(ingredientToUpdate);

    }

    public boolean isValidName(Ingredient ingredient){
        return !ingredientRepository.existsByName(ingredient.getName());
    }

    public boolean deleteById(Integer id) throws IngredientNotFoundException{
        ingredientRepository.findById(id).orElseThrow(()->new IngredientNotFoundException(Integer.toString(id)));
        try{
            ingredientRepository.deleteById(id);
            return true;
        } catch(Exception e){
            return false;
        }
    }
}
