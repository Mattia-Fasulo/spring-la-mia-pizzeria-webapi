package org.exercise.pizzeria.api;

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
}
