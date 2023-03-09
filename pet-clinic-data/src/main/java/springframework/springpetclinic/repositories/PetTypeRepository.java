package springframework.springpetclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import springframework.springpetclinic.model.PetType;

public interface PetTypeRepository extends CrudRepository<PetType, Long> {

}
