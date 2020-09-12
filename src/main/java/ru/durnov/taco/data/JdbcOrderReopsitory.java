package ru.durnov.taco.data;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.durnov.taco.Order;
import ru.durnov.taco.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.*;

@Repository
public class JdbcOrderReopsitory implements OrderRepository{
    private SimpleJdbcInsert orderInserter;
    private SimpleJdbcInsert orderTacoInserter;
    private ObjectMapper objectMapper;

    @Autowired
    public JdbcOrderReopsitory(JdbcTemplate jdbcTemplate) {
        this.orderInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("Taco_Order").usingGeneratedKeyColumns("id");
        this.orderTacoInserter = new SimpleJdbcInsert(jdbcTemplate).withTableName("Taco_Order_Tacos");
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public Order save(Order order) {
        order.setPlacedAt(new Date());
        long orderId = saveOrderDetails(order);
        order.setId(orderId);
        List<Taco> tacos = order.getTacos();
        for (Taco taco: tacos){
            saveTacoToOrder(taco, orderId);
        }
        return order;
    }

    @Override
    public List<Order> findByZip(String zip) {
        return null;
    }

    @Override
    public List<Order> readOrdersByZipAndPlacedAtBetween(String zip, Date startDate, Date endDate) {
        return null;
    }

    private long saveOrderDetails(Order order){
        @SuppressWarnings("unchecked")
        Map<String,Object> values = objectMapper.convertValue(order, Map.class);
        values.put("placedAt", order.getPlacedAt());
        long orderId = orderInserter.executeAndReturnKey(values).longValue();
        return orderId;
    }

    private void saveTacoToOrder(Taco taco, long orderId){
        Map<String, Object> values = new HashMap<>();
        values.put("tacoOrder", orderId);
        values.put("taco", taco.getId());
        orderTacoInserter.execute(values);
    }
}
