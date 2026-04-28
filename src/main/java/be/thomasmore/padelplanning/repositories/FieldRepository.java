package be.thomasmore.padelplanning.repositories;

import be.thomasmore.padelplanning.model.Field;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FieldRepository extends CrudRepository<Field, Integer> {
    @Query("SELECT f FROM Field f")
    List<Field> getAvailable();
}
