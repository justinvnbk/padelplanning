package be.thomasmore.padelplanning.repositories;

import be.thomasmore.padelplanning.model.PadelDay;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface PadelDayRepository extends CrudRepository<PadelDay, Integer> {
    @Query("SELECT pd FROM PadelDay pd WHERE pd.date >= :now ORDER BY pd.date DESC LIMIT 1")
    Optional<PadelDay> getLast(@Param("now") LocalDateTime now);
}
