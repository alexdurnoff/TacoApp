package ru.durnov.taco.integration;

import org.springframework.integration.annotation.Splitter;
import org.springframework.stereotype.Component;
import ru.durnov.taco.Order;

import java.util.ArrayList;
import java.util.Collection;


public class OrderSplitter {
    Collection<Object> splitOrderIntoParts(PurshaseOrder po){
        ArrayList<Object> parts = new ArrayList<>();
        parts.add(po.getBillingInfo());
        parts.add(po.getLineItems());
        return parts;
    }
}
