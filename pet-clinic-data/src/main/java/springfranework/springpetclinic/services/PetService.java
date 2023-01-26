package springfranework.springpetclinic.services;

import springfranework.springpetclinic.model.Pet;

import java.util.Set;

public interface PetService {
    Pet findById(long id);

    Pet save(Pet pet);

    Set<Pet> findAll();
}
