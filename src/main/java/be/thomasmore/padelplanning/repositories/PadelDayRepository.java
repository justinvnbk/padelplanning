package be.thomasmore.padelplanning.repositories;

import be.thomasmore.padelplanning.model.PadelDay;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PadelDayRepository extends CrudRepository<PadelDay, Integer> {
    @Query("SELECT pd FROM PadelDay pd WHERE pd.date >= :now AND pd.isPublished = true ORDER BY pd.date LIMIT 1")
    Optional<PadelDay> getNext(@Param("now") LocalDateTime now);

    @Query("SELECT pd FROM PadelDay pd ORDER BY pd.date ASC")
    List<PadelDay> findAllOrdered();

    @Query("SELECT pd FROM PadelDay pd WHERE pd.date >= :now - 120 MINUTE ORDER BY pd.date ASC")
    List<PadelDay> getUpcomingPadelDays(@Param("now") LocalDateTime now);
}
