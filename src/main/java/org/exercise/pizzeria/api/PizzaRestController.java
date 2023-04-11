package org.exercise.pizzeria.api;

import jakarta.validation.Valid;
import org.exercise.pizzeria.exceptions.PizzaNotFoundException;
import org.exercise.pizzeria.model.Pizza;
import org.exercise.pizzeria.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/pizzas")
public class PizzaRestController {

    @Autowired
    private PizzaService pizzaService;

    // all pizzas
    @GetMapping
    public List<Pizza> list(
            @RequestParam(name = "q")Optional<String> search
            ){
        if(search.isPresent()){
            return pizzaService.getByName(search.get());
        }
        return pizzaService.getAll();
    }

    // details pizza
    @GetMapping("/{id}")
    public Pizza getById(
            @PathVariable Integer id
    ){
        try{
            return  pizzaService.getById(id);
        } catch (PizzaNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

    }

    // create pizza
    @PostMapping
    public Pizza create(
            @Valid @RequestBody Pizza pizza
    ){
        if(!pizzaService.isValidName(pizza)){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "{\"errors\": \"the name must be unique\"}");
        }
        return pizzaService.createPizza(pizza);
    }

    // update pizza
    @PutMapping("/{id}")
    public Pizza update(
            @PathVariable Integer id,
            @Valid @RequestBody Pizza pizza
    ){
        if(!pizzaService.isValidName(pizza)){
            throw  new ResponseStatusException(HttpStatus.BAD_REQUEST, "{\"errors\": \"the name must be unique\"}");
        }
        try{
            return pizzaService.updatePizza(pizza, id);
        } catch (PizzaNotFoundException e){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    // delete pizza
    @PostMapping("/{id}")
    public void delete(
            @PathVariable Integer id
    ){
        try{
            boolean success = pizzaService.deleteById(id);
            if (!success){
                throw new ResponseStatusException(HttpStatus.CONFLICT, "Unable to delete pizza because it has offers");
            }
        } catch (PizzaNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

}
