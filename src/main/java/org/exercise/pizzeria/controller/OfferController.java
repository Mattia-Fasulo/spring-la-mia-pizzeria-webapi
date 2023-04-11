package org.exercise.pizzeria.controller;

import jakarta.validation.Valid;
import org.exercise.pizzeria.exceptions.OfferNotFoundException;
import org.exercise.pizzeria.exceptions.PizzaNotFoundException;
import org.exercise.pizzeria.model.Offer;
import org.exercise.pizzeria.model.Pizza;
import org.exercise.pizzeria.services.OfferService;
import org.exercise.pizzeria.services.PizzaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.Optional;

@Controller
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    private PizzaService pizzaService;

    @Autowired
    private OfferService offerService;

    @GetMapping("/create")
    public String create(
            @RequestParam(name = "pizzaId") Integer id,
            Model model
    ) {
        Offer offer = new Offer();
        offer.setStartDate(LocalDate.now());
        offer.setEndDate(LocalDate.now().plusMonths(1));

        try{
            Pizza pizza = pizzaService.getById(id);
            offer.setPizza(pizza);
        } catch (PizzaNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        model.addAttribute("offer", offer);

        return "/offers/create";
    }

    @PostMapping("/create")
    public String doCreate(
            @Valid @ModelAttribute Offer formOffer,
            BindingResult bindingResult
    ){

        if (bindingResult.hasErrors()){
            return "/offers/create";
        }

        Offer createdOffer = offerService.create(formOffer);
        return "redirect:/pizzas/" + Integer.toString(createdOffer.getPizza().getId());
    }

    @GetMapping("/edit/{id}")
    public String edit(
            @PathVariable Integer id,
            Model model
    ){

        try{
            Offer offer = offerService.getById(id);
            model.addAttribute("offer", offer);
            return "/offers/create";
        } catch (OfferNotFoundException e){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found");
        }

    }

    @PostMapping("/edit/{id}")
    public String doEdit(
            @PathVariable Integer id,
            @Valid @ModelAttribute("offer") Offer formOffer,
            BindingResult bindingResult
    ){
        if(bindingResult.hasErrors()){
            return "/offers/create";
        }

        try{
            Offer updateOffer = offerService.updateOffer(formOffer, id);
            return "redirect:/pizzas/" + Integer.toString(formOffer.getPizza().getId());
        } catch(PizzaNotFoundException e) {
            throw  new ResponseStatusException(HttpStatus.NOT_FOUND, "Offer not found");
        }
    }
}
