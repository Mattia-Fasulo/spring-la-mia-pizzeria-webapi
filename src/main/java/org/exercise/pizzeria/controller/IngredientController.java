package org.exercise.pizzeria.controller;

import jakarta.validation.Valid;
import org.exercise.pizzeria.exceptions.IngredientNotFoundException;
import org.exercise.pizzeria.model.AlertMessage;
import org.exercise.pizzeria.model.Ingredient;
import org.exercise.pizzeria.services.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/ingredients")
public class IngredientController {
    @Autowired
    private IngredientService ingredientService;

    @GetMapping
    public String index(Model model){
        model.addAttribute("list", ingredientService.getAll());
        model.addAttribute("ingredientObj", new Ingredient());
        return "/ingredients/index";
    }

    @PostMapping("/save")
    public String doSave(
            @Valid @ModelAttribute(name = "ingredientObj") Ingredient formIngredient,
            BindingResult bindingResult,
            Model model
            ){
        if (!ingredientService.isValidName(formIngredient)){
            bindingResult.addError(new FieldError("ingredient","name", formIngredient.getName(),false,null,null,"name must be unique"));
        }

        if(bindingResult.hasErrors()){
            model.addAttribute("list", ingredientService.getAll());
            return  "/ingredients/index";
        }

        ingredientService.create(formIngredient);
        return "redirect:/ingredients";
    }

    @GetMapping("/edit")
    public String edit(
            @RequestParam(name = "ingredientId") Integer id,
            Model model
    ){
        try{
            Ingredient ingredient = ingredientService.getById(id);
            model.addAttribute("list", ingredientService.getAll());
            model.addAttribute("ingredientObj", ingredient);
            return "/ingredients/index";
        } catch (IngredientNotFoundException e){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/edit/{id}")
    public String doEdit(
            @PathVariable Integer id,
            @Valid @ModelAttribute(name = "ingredientObj") Ingredient formIngredient,
            BindingResult bindingResult
    ){
        if (!ingredientService.isValidName(formIngredient)){
            bindingResult.addError(new FieldError("ingredient","name",formIngredient.getName(),false,null,null,"name must be unique"));
        }
        if(bindingResult.hasErrors()){
            return "/ingredients/index";
        }

        try{
            Ingredient updateIngredient = ingredientService.update(formIngredient, id);
            return "redirect:/ingredients";
        } catch (IngredientNotFoundException e){
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Ingredient not found");
        }

    }

    @GetMapping("/delete/{id}")
    public String delete(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ){
        try {
            boolean success = ingredientService.deleteById(id);
            if(success){
                    redirectAttributes.addFlashAttribute("message",
                            new AlertMessage(AlertMessage.AlertMessageType.SUCCESS, "Ingredient with id " + id + " deleted"));
            } else {
                    redirectAttributes.addFlashAttribute("message",
                            new AlertMessage(AlertMessage.AlertMessageType.ERROR, "Unable to delete ingredient with id " + id));
            }

        } catch(IngredientNotFoundException e){
            redirectAttributes.addFlashAttribute("message",
                    new AlertMessage(AlertMessage.AlertMessageType.ERROR, "Ingredient with id " + id + " not found"));
        }
        return "redirect:/ingredients";
    }


}
