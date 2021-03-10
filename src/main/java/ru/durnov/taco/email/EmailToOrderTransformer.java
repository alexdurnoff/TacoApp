package ru.durnov.taco.email;

import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class EmailToOrderTransformer extends AbstractMailMessageTransformer<Order> {
    private static final String SUBJECT_KEYWORDS = "TACO ORDER";


    @Override
    protected AbstractIntegrationMessageBuilder<Order> doTransform(javax.mail.Message message)
            throws Exception {
        Order tacoOrder = processPayload(message);
        if (tacoOrder != null) return MessageBuilder.withPayload(tacoOrder);
        return MessageBuilder.withPayload(new Order("door1975@yandex.ru"));
    }

    private Order processPayload(Message message) {
        try{
            String subject = message.getSubject();
            if(subject.toUpperCase().contains(SUBJECT_KEYWORDS)){
                String email = ((InternetAddress) message.getFrom()[0]).getAddress();
                String content = message.getContent().toString();
                return parseEmailToOrder(email, content);
            }
        } catch (MessagingException | IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private Order parseEmailToOrder(String email, String content) {
        Order order = new Order(email);
        String[] lines = content.split("\\r?\\n");
        for(String line:lines){
            if (line.trim().length()>0 && line.contains(":")){
                String[] lineSplit = line.split(":");
                String tacoName = lineSplit[0].trim();
                String ingredients = lineSplit[1].trim();
                String[] ingredientsSplit = ingredients.split(",");
                List<String> ingredientCodes = new ArrayList<>();
                for(String ingredientName:ingredientsSplit){
                    String code = lookupIngredientCode(ingredientName.trim());
                    if(code !=null){
                        ingredientCodes.add(code);
                    }
                }
                Taco taco = new Taco(email);
                taco.setIngredients(ingredientCodes);
                order.addTaco(taco);
            }
        }
        return order;
    }

    private String lookupIngredientCode(String ingredientName) {
        for (Ingredient ingredient : ALL_INGREDIENTS) {
            String ucIngredientName = ingredientName.toUpperCase();
            if (LevenshteinDistance.getDefaultInstance().apply(ucIngredientName, ingredient.getName()) < 3 ||
                    ucIngredientName.contains(ingredient.getName()) ||
                    ingredient.getName().contains(ucIngredientName)) {
                return ingredient.getCode();

            }
        }
        return null;
    }

    private static Ingredient[] ALL_INGREDIENTS = new Ingredient[]{
            new Ingredient("FLTO", "FLOUR TORTILLA"),
            new Ingredient("COTO", "CORN TORTILLA"),
            new Ingredient("GRBF", "GROUND BEEF"),
            new Ingredient("CARN", "CARNITAS"),
            new Ingredient("TMTO", "TOMATOES"),
            new Ingredient("LETC", "LETTUCE"),
            new Ingredient("CHED", "CHEDDAR"),
            new Ingredient("JACK", "MONTERREY JACK"),
            new Ingredient("SLSA", "SALSA"),
            new Ingredient("SRCR", "SOUR CREAM")
    };
}
