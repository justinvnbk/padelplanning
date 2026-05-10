package be.thomasmore.padelplanning.repositories;

import be.thomasmore.padelplanning.model.Notification;
import org.springframework.data.repository.CrudRepository;

public interface NotificationRepository extends CrudRepository<Notification, Integer> {
}
