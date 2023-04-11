package org.exercise.pizzeria.services;

import org.exercise.pizzeria.exceptions.PizzaNotFoundException;
import org.exercise.pizzeria.model.Pizza;
import org.exercise.pizzeria.repository.PizzaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PizzaService {

    @Autowired
    private PizzaRepository pizzaRepository;

    public List<Pizza> getAll(){
        return pizzaRepository.findAll();
    }

    public List<Pizza> getByName(String keyword){
        return pizzaRepository.findByNameContainingIgnoreCase(keyword);
    }

    public Pizza getById(Integer id) throws PizzaNotFoundException{

        //pizzaRepository.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND))

        Optional<Pizza> result = pizzaRepository.findById(id);
        if (result.isEmpty()) {
            throw new PizzaNotFoundException("Pizza not found");
        } else {
            return result.get();
        }

    }

    public boolean deleteById(Integer id) throws PizzaNotFoundException{
        pizzaRepository.findById(id).orElseThrow(()->new PizzaNotFoundException(Integer.toString(id)));
        try{
            pizzaRepository.deleteById(id);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    public List<Pizza> getByDescriptionAndPrice(Optional<String> ingredients, Optional<BigDecimal> price){
        return pizzaRepository.findByDescriptionContainingAndPriceIsLessThanEqual(ingredients.orElse(null), price.orElse(BigDecimal.valueOf(1000)));
    }

    public Pizza createPizza(Pizza formPizza){


        Pizza pizzaToPersist = new Pizza();
        pizzaToPersist.setName(formPizza.getName());
        pizzaToPersist.setDescription(formPizza.getDescription());
        pizzaToPersist.setPrice(formPizza.getPrice());
        pizzaToPersist.setImgPath(formPizza.getImgPath());
        pizzaToPersist.setCreatedAt(LocalDateTime.now());
        pizzaToPersist.setIngredients(formPizza.getIngredients());
        return  pizzaRepository.save(pizzaToPersist);

    }

    public Pizza updatePizza(Pizza formPizza, Integer id){
        Pizza pizzaToUpdate = getById(id);
        pizzaToUpdate.setName(formPizza.getName());
        pizzaToUpdate.setDescription(formPizza.getDescription());
        pizzaToUpdate.setPrice(formPizza.getPrice());
        pizzaToUpdate.setImgPath(formPizza.getImgPath());
        pizzaToUpdate.setIngredients(formPizza.getIngredients());
        return pizzaRepository.save(pizzaToUpdate);
    }

    public boolean isValidName(Pizza pizzaToValidate){
        if(pizzaToValidate.getId() == null){
            return !pizzaRepository.existsByName(pizzaToValidate.getName());
        }
        return !pizzaRepository.existsByNameAndIdNot(pizzaToValidate.getName(), pizzaToValidate.getId());
    }


}
