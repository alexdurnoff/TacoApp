package ru.durnov.taco.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import ru.durnov.taco.Order;

@Service
public class KafkaOrderMessageService implements OrderMessagingService{
    private final KafkaTemplate<String, Order> kafkaTemplate;

    @Autowired
    public KafkaOrderMessageService(KafkaTemplate<String,Order> kafkaTemplate){
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendOrder(Order order) {
        kafkaTemplate.send("tacocloud.orders.topic", order);
    }
}
