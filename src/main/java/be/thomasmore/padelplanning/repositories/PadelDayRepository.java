package be.thomasmore.padelplanning.repositories;

import be.thomasmore.padelplanning.model.PadelDay;
import org.springframework.data.repository.CrudRepository;

public interface PadelDayRepository extends CrudRepository<PadelDay, Integer> {
}
