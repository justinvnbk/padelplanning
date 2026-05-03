package be.thomasmore.padelplanning.repositories;

import be.thomasmore.padelplanning.model.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
    @Query("SELECT p FROM Player p")
    List<Player> getAll();

    @Query("SELECT p FROM Player p WHERE p.email = :email")
    Player findByEmail(@Param("email") String email);
}
