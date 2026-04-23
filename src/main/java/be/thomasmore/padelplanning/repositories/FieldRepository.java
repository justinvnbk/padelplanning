package be.thomasmore.padelplanning.repositories;

import be.thomasmore.padelplanning.entities.Team;
import org.springframework.data.repository.CrudRepository;

public interface FieldRepository extends CrudRepository<Team, Integer> {
}
