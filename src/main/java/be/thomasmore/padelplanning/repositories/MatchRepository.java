package be.thomasmore.padelplanning.repositories;

import be.thomasmore.padelplanning.entities.Match;
import org.springframework.data.repository.CrudRepository;

public interface MatchRepository extends CrudRepository<Match, Integer> {
}
