package springframework.springpetclinic.services.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import springframework.springpetclinic.model.Pet;
import springframework.springpetclinic.repositories.PetRepository;
import springframework.springpetclinic.services.PetService;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("javaspringjpa")
public class PetJpaService implements PetService {

	private final PetRepository petRepository;

	public PetJpaService(PetRepository petRepository) {
		this.petRepository = petRepository;
	}

	@Override
	public Set<Pet> findAll() {
		Set<Pet> pets = new HashSet<>();
		petRepository.findAll().forEach(pets::add);

		return pets;
	}

	@Override
	public Pet findById(Long aLong) {
		return petRepository.findById(aLong).orElse(null);
	}

	@Override
	public Pet save(Pet object) {
		return petRepository.save(object);
	}

	@Override
	public void delete(Pet object) {
		petRepository.delete(object);
	}

	@Override
	public void deleteById(Long aLong) {
		petRepository.deleteById(aLong);
	}

}
