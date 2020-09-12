package ru.durnov.taco.data;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.durnov.taco.Order;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
	Order save(Order order);

	List<Order> findByZip(String zip);
	List<Order> readOrdersByZipAndPlacedAtBetween(String zip, Date startDate, Date endDate);
}
