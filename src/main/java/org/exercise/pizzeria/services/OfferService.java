package org.exercise.pizzeria.services;

import org.exercise.pizzeria.exceptions.OfferNotFoundException;
import org.exercise.pizzeria.model.Offer;
import org.exercise.pizzeria.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OfferService {
    @Autowired
    private OfferRepository offerRepository;

    public Offer create(Offer formOffer){
        return offerRepository.save(formOffer);
    }

    public Offer getById(Integer id) throws OfferNotFoundException {
        Optional<Offer> result = offerRepository.findById(id);

        if(result.isEmpty()){
            throw new OfferNotFoundException("Offer not found");
        } else {
            return result.get();
        }


    }

    public Offer updateOffer(Offer formOffer, Integer id){
        Offer offerToUpdate = getById(id);
        offerToUpdate.setTitle(formOffer.getTitle());
        offerToUpdate.setStartDate(formOffer.getStartDate());
        offerToUpdate.setEndDate(formOffer.getEndDate());
        offerToUpdate.setPizza(formOffer.getPizza());
        return offerRepository.save(offerToUpdate);
    }
}
