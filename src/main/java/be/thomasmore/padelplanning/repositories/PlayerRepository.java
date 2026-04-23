package be.thomasmore.padelplanning.repositories;

import be.thomasmore.padelplanning.entities.Player;
import org.springframework.data.repository.CrudRepository;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
}
