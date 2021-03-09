package ru.durnov.taco.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MessageConverter;
import ru.durnov.taco.Order;

import javax.jms.JMSException;
import javax.jms.Message;

public class JmsOrderReceiver implements OrderReceiver{
    private final JmsTemplate jmsTemplate;
    private final MessageConverter messageConverter;

    @Autowired
    public JmsOrderReceiver(JmsTemplate jmsTemplate, MessageConverter messageConverter){
        this.jmsTemplate=jmsTemplate;
        this.messageConverter = messageConverter;
    }

    @Override
    public Order receiveOrder() throws JMSException {
        Message message = jmsTemplate.receive("tacocloud.order.queue");
        Order order = (Order) this.messageConverter.fromMessage(message);
        return order;
    }
}
