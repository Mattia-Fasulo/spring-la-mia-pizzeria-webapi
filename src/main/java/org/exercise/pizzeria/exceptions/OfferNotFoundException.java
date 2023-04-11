package org.exercise.pizzeria.exceptions;

public class OfferNotFoundException extends RuntimeException{
    public OfferNotFoundException(String message){
        super(message);
    }
}
