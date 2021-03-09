package ru.durnov.taco.messaging;

import ru.durnov.taco.Order;

import javax.jms.JMSException;

public interface OrderReceiver {
    Order receiveOrder() throws JMSException;
}
