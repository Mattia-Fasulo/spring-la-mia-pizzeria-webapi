package org.exercise.pizzeria.api;

import org.exercise.pizzeria.model.Pizza;
import org.exercise.pizzeria.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin
@RequestMapping("/api/pizzas")
public class PizzaRestController {

    @Autowired
    private PizzaService pizzaService;

    @GetMapping
    public List<Pizza> list(
            @RequestParam(name = "q")Optional<String> search
            ){
        if(search.isPresent()){
            return pizzaService.getByName(search.get());
        }
        return pizzaService.getAll();
    }
}
