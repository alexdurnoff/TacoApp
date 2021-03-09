package ru.durnov.taco.messaging;

import ru.durnov.taco.Order;

public interface OrderMessagingService {
    void sendOrder(Order order);
}
