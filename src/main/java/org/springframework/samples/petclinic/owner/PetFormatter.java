package org.springframework.samples.petclinic.owner;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.Formatter;
import org.springframework.samples.petclinic.visit.VisitController;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Collection;
import java.util.Locale;

@Component
public class PetFormatter implements Formatter<Integer> {

    private final PetRepository pets;

    @Autowired
    public PetFormatter(PetRepository pets) {
        this.pets = pets;
    }

    @Override
    public String print(Integer id, Locale local) {
        return pets.findById(id).getName();
    }

    @Override
    public Integer parse(String value, Locale local) throws ParseException {
        Collection<Pet> pets = this.pets.findByOwnerId(VisitController.ownerId);
        for (Pet i : pets) {
            if (i.getName().equals(value)) {
                return i.getId();
            }
        }
        throw new ParseException("Pet not found: " + value, 0);
    }

}
