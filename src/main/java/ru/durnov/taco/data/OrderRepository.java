package ru.durnov.taco.data;

import ru.durnov.taco.Order;

public interface OrderRepository {
	Order save(Order order);
}
