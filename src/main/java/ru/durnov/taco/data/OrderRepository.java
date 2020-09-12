package ru.durnov.taco.data;

import ru.durnov.taco.Order;

import java.util.Date;
import java.util.List;

public interface OrderRepository {
	Order save(Order order);

	List<Order> findByZip(String zip);
	List<Order> readOrdersByZipAndPlacedAtBetween(String zip, Date startDate, Date endDate);
}
