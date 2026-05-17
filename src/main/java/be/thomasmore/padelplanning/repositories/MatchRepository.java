package be.thomasmore.padelplanning.repositories;

import be.thomasmore.padelplanning.model.Match;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalTime;

public interface MatchRepository extends CrudRepository<Match, Integer> {
    //Used count because we don't require the whole object
    @Query("SELECT COUNT(m) > 0 FROM Match m " +
            "JOIN m.teams t " +
            "JOIN t.players p " +
            "WHERE m.timeSlot = :time " +
            "AND p.id = :playerId ")
    boolean isPlayerBusy(@Param("time") LocalTime time,
                         @Param("playerId") Integer playerId);
}
