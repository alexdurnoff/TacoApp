package ru.durnov.taco.messaging;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.durnov.taco.Order;

@Component
public class RabbitOrderListener {
    private final KitchenUi ui;
    private final Logger logger = (Logger) LogManager.getLogger(RabbitsOrderReceiver.class);

    @Autowired
    public RabbitOrderListener(KitchenUi ui){
        this.ui = ui;
    }

    @RabbitListener(queues = "tacocloud.order.queue")
    public void receiveOrder(Order order, ConsumerRecord<String, Order> consumerRecord){
        logger.info("Received from partition " + consumerRecord.partition() + "with timestamp " + consumerRecord.timestamp() );
        ui.displayOrder(order);
    }
}
