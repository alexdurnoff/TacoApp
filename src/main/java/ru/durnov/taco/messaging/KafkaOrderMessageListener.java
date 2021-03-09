package ru.durnov.taco.messaging;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import ru.durnov.taco.Order;

@Component
public class KafkaOrderMessageListener{
    private final KitchenUi ui;

    public KafkaOrderMessageListener(KitchenUi ui){
        this.ui = ui;
    }

    @KafkaListener(topics = "tacocloud.orders.topic")
    public void handle(Order order){
        ui.displayOrder(order);
    }

}
