package be.thomasmore.padelplanning.repositories;

import be.thomasmore.padelplanning.entities.Field;
import be.thomasmore.padelplanning.entities.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Collection;
import java.util.List;

public interface FieldRepository extends CrudRepository<Field, Integer> {
    @Query("SELECT f FROM Field f")
    List<Field> getAvailable();
}
