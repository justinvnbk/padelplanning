package be.thomasmore.padelplanning.repositories;

import be.thomasmore.padelplanning.model.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Integer> {
}
