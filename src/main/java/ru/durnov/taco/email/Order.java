package ru.durnov.taco.email;

import lombok.Data;


import java.util.ArrayList;
import java.util.List;

@Data
public class Order {
    private String email;
    private List<Taco> tacos = new ArrayList<>();

    public Order(String email){this.email = email;}

    public void addTaco(Taco taco){
        this.tacos.add(taco);
    }

}
