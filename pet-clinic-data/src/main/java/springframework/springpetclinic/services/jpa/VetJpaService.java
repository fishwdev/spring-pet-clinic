package springframework.springpetclinic.services.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import springframework.springpetclinic.model.Vet;
import springframework.springpetclinic.repositories.VetRepository;
import springframework.springpetclinic.services.VetService;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("javaspringjpa")
public class VetJpaService implements VetService {

	private VetRepository vetRepository;

	public VetJpaService(VetRepository vetRepository) {
		this.vetRepository = vetRepository;
	}

	@Override
	public Set<Vet> findAll() {
		Set<Vet> vets = new HashSet<>();
		vetRepository.findAll().forEach(vets::add);

		return vets;
	}

	@Override
	public Vet findById(Long aLong) {
		return vetRepository.findById(aLong).orElse(null);
	}

	@Override
	public Vet save(Vet object) {
		return vetRepository.save(object);
	}

	@Override
	public void delete(Vet object) {
		vetRepository.delete(object);
	}

	@Override
	public void deleteById(Long aLong) {
		vetRepository.deleteById(aLong);
	}

}
