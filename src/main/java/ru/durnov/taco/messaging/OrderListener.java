package ru.durnov.taco.messaging;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import ru.durnov.taco.Order;

@Component
public class OrderListener {
    private final KitchenUi kitchenUi;

    @Autowired
    public OrderListener(KitchenUi kitchenUi){
        this.kitchenUi = kitchenUi;
    }

    @JmsListener(destination = "tacocloud.order.queue")
    public void receiveOrder(Order order){
        kitchenUi.displayOrder(order);
    }
}
