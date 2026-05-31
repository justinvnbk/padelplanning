package be.thomasmore.padelplanning.repositories;

import be.thomasmore.padelplanning.model.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PlayerRepository extends CrudRepository<Player, Integer> {
    @Query("SELECT p FROM Player p")
    List<Player> getAll();

    @Query("SELECT p FROM Player p WHERE p.email = :email ")
    Player findByEmail(@Param("email") String email);

    @Query("SELECT p FROM Player p WHERE p.id IN :ids ORDER BY p.name")
    List<Player> findAllByIds(@Param("ids") Iterable<Integer> ids);

    @Query(value = "SELECT p.* FROM player p " +
            "JOIN authorities a ON p.email = a.username " +
            "WHERE a.authority = 'ADMIN'",
            nativeQuery = true)
    List<Player> findAllAdmins();

    @Query("SELECT p FROM Player p WHERE NOT p.isApproved ORDER BY LOWER(p.name)")
    List<Player> getPendingPlayers();

    @Query("SELECT p FROM Player p WHERE p.isApproved ORDER BY LOWER(p.name)")
    List<Player> getApprovedPlayers();
}