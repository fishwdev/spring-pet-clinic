package springframework.springpetclinic.services.jpa;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import springframework.springpetclinic.model.Speciality;
import springframework.springpetclinic.repositories.SpecialityRepository;
import springframework.springpetclinic.services.SpecialityService;

import java.util.HashSet;
import java.util.Set;

@Service
@Profile("javaspringjpa")
public class SpecialityJpaService implements SpecialityService {

	private final SpecialityRepository specialityRepository;

	public SpecialityJpaService(SpecialityRepository specialityRepository) {
		this.specialityRepository = specialityRepository;
	}

	@Override
	public Set<Speciality> findAll() {
		Set<Speciality> specialities = new HashSet<>();
		specialityRepository.findAll().forEach(specialities::add);
		return specialities;
	}

	@Override
	public Speciality findById(Long aLong) {
		return specialityRepository.findById(aLong).orElse(null);
	}

	@Override
	public Speciality save(Speciality object) {
		return specialityRepository.save(object);
	}

	@Override
	public void delete(Speciality object) {
		specialityRepository.delete(object);
	}

	@Override
	public void deleteById(Long aLong) {
		specialityRepository.deleteById(aLong);
	}

}
