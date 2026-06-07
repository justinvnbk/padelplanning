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

    @Query("SELECT p FROM Player p WHERE NOT p.isApproved AND (p.name ILIKE %:keyword% OR p.email ILIKE %:keyword%) ORDER BY LOWER(p.name)")
    List<Player> getPendingPlayers(@Param("keyword") String keyword);

    @Query("SELECT p FROM Player p WHERE p.isApproved AND (p.name ILIKE %:keyword% OR p.email ILIKE %:keyword%) ORDER BY LOWER(p.name)")
    List<Player> getApprovedPlayers(@Param("keyword") String keyword);

    @Query("SELECT player FROM ClubEvent clubEvent JOIN clubEvent.participants player WHERE clubEvent.id = :eventId AND (LOWER(player.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(player.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR LOWER(player.telephone) LIKE LOWER(CONCAT('%', :searchTerm, '%')))ORDER BY player.name")
    List<Player> findParticipantsByEventIdAndSearchTerm(
            @Param("eventId") Integer eventId,
            @Param("searchTerm") String searchTerm
    );
}