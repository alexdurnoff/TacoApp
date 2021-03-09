package ru.durnov.taco.messaging;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import ru.durnov.taco.Order;

import javax.jms.JMSException;

public class RabbitsOrderReceiver implements OrderReceiver{
    private final RabbitTemplate rabbitTemplate;
    private final MessageConverter messageConverter;

    @Autowired
    public RabbitsOrderReceiver(RabbitTemplate rabbitTemplate, MessageConverter messageConverter){
        this.rabbitTemplate = rabbitTemplate;
        this.messageConverter = messageConverter;
    }

    @Override
    public Order receiveOrder() throws JMSException {
        return (Order) this.rabbitTemplate.receiveAndConvert("tacocloud.order.queue");
    }
}
