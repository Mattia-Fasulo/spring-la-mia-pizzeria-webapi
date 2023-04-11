package org.exercise.pizzeria.controller;

import jakarta.validation.Valid;
import org.exercise.pizzeria.exceptions.PizzaNotFoundException;
import org.exercise.pizzeria.model.AlertMessage;
import org.exercise.pizzeria.model.AlertMessage.AlertMessageType;
import org.exercise.pizzeria.model.Pizza;
import org.exercise.pizzeria.repository.IngredientRepository;
import org.exercise.pizzeria.services.IngredientService;
import org.exercise.pizzeria.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/pizzas")
public class PizzaController {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private IngredientService ingredientService;


    @GetMapping
    public String index(Model model,
                        @RequestParam(name = "q") Optional<String> keyword
                        ) {
        List<Pizza> pizzas;
        if(keyword.isEmpty()){
            pizzas = pizzaService.getAll();
        } else {
            pizzas = pizzaService.getByName(keyword.get());
            model.addAttribute("keyword", keyword.get());
        }

        model.addAttribute("list", pizzas);
        return "/pizzas/index";
    }

    @GetMapping("/{pizzaId}")
    public String show(Model model, @PathVariable("pizzaId") Integer id ){
        Pizza p = pizzaService.getById(id);
        model.addAttribute("pizza", p);
        return "/pizzas/show";
    }


    //search
    @GetMapping("/search")
    public String search(){
        return "/pizzas/search";
    }

    @GetMapping("/search/result")
    public String searchResult(Model model,
                               @RequestParam(name = "i") Optional<String> ingredients,
                               @RequestParam(name = "p") Optional<BigDecimal> price){
        List<Pizza> pizzas = pizzaService.getByDescriptionAndPrice(ingredients, price);
        model.addAttribute("list", pizzas);
        return "/pizzas/index";
    }


    //create
    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("pizza", new Pizza());
        model.addAttribute("ingredients", ingredientService.getAll());
        return "/pizzas/create";
    }

    @PostMapping("/create")
    public String doCreate(Model model,
                           @Valid @ModelAttribute("pizza") Pizza formPizza,
                           BindingResult bindingResult) {

        if (!pizzaService.isValidName(formPizza)) {
            bindingResult.addError(new FieldError("pizza", "name", formPizza.getName(), false, null, null,
                    "name must be unique"));
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("ingredients", ingredientService.getAll());
            return "/pizzas/create";
        }

        System.out.println(formPizza.getIngredients());
        pizzaService.createPizza(formPizza);
        return "redirect:/pizzas";
    }




    // edit
    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable Integer id,
            Model model
    ){
        try {
           Pizza pizza = pizzaService.getById(id);
           model.addAttribute("pizza", pizza);
            model.addAttribute("ingredients", ingredientService.getAll());
           return "/pizzas/create";
        } catch(PizzaNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza with id " + id + " not found");
        }

    }

    @PostMapping("/edit/{id}")
    public String doEdit(
            @PathVariable Integer id,
            @Valid @ModelAttribute("pizza") Pizza formPizza,
            BindingResult bindingResult, Model model){
        if (!pizzaService.isValidName(formPizza)) {
            bindingResult.addError(new FieldError("pizza", "name", formPizza.getName(), false, null, null,
                    "name must be unique"));
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("ingredients", ingredientService.getAll());
            return "/pizzas/create";
        }
        try{
            Pizza updatePizza = pizzaService.updatePizza(formPizza, id);
            return "redirect:/pizzas/" + Integer.toString(updatePizza.getId());
        } catch(PizzaNotFoundException e) {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "pizza with id " + id + " not found");
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ){
        try{
            boolean success = pizzaService.deleteById(id);
            if(success){
                redirectAttributes.addFlashAttribute("message",
                        new AlertMessage(AlertMessageType.SUCCESS, "Pizza with id " + id + " deleted"));
            } else {
                redirectAttributes.addFlashAttribute("message",
                        new AlertMessage(AlertMessageType.ERROR, "Unable to delete pizza with id " + id));
            }
        } catch(PizzaNotFoundException e) {
            redirectAttributes.addFlashAttribute("message",
                    new AlertMessage(AlertMessageType.ERROR, "Pizza with id " + id + " not found"));
        }
        return "redirect:/pizzas";
    }

}
