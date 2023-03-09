package springframework.springpetclinic.repositories;

import org.springframework.data.repository.CrudRepository;
import springframework.springpetclinic.model.Visit;

public interface VisitRepository extends CrudRepository<Visit, Long> {

}
