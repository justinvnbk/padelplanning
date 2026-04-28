package be.thomasmore.padelplanning.repositories;

import be.thomasmore.padelplanning.model.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
    @Query("SELECT p FROM Player p")
    List<Player> getAll();
}
