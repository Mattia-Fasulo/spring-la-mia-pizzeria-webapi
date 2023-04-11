package org.exercise.pizzeria.controller;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

@Controller
@RequestMapping("/")
public class IndexController {

    @GetMapping
    public String index(){
        //throw new ResponseStatusException(HttpStatus.NOT_IMPLEMENTED, "My custom error");
        return "redirect:/pizzas";
    }
}
