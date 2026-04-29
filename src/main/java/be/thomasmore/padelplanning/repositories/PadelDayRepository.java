package be.thomasmore.padelplanning.repositories;

import be.thomasmore.padelplanning.model.PadelDay;
import be.thomasmore.padelplanning.model.Player;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PadelDayRepository extends CrudRepository<PadelDay, Integer> {
    @Query("SELECT pd FROM PadelDay pd ORDER BY pd.date DESC LIMIT 1")
    PadelDay getLast();
}
