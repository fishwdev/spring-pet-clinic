package springframework.springpetclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import springframework.springpetclinic.model.Speciality;

public interface SpecialityRepository extends CrudRepository<Speciality, Long> {
}
