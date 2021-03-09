package ru.durnov.taco.messaging;

import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.support.converter.MessageConverter;
import ru.durnov.taco.Order;

public class RabbitMessagingService implements OrderMessagingService{
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitMessagingService(RabbitTemplate rabbitTemplate){
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public void sendOrder(Order order) {
        MessageConverter messageConverter = rabbitTemplate.getMessageConverter();
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("X_ORDER_SOURCE", "WEB");
        Message message = messageConverter.toMessage(order, messageProperties);
        rabbitTemplate.send(message);
        rabbitTemplate.convertAndSend("tacocloud.order", order);
    }
}
