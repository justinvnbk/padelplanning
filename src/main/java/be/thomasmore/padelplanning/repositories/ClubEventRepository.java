package be.thomasmore.padelplanning.repositories;

import be.thomasmore.padelplanning.model.ClubEvent;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ClubEventRepository extends CrudRepository<ClubEvent, Integer> {

    @Query("SELECT event FROM ClubEvent event ORDER BY event.startDateTime DESC")
    List<ClubEvent> findAllOrdered();

    @Query("SELECT event FROM ClubEvent event WHERE event.published = true AND event.startDateTime >= CURRENT_TIMESTAMP ORDER BY event.startDateTime")
    List<ClubEvent> findUpcomingPublishedEvents();
}
