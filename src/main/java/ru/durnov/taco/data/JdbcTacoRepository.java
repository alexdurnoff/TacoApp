package ru.durnov.taco.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import ru.durnov.taco.Ingredient;
import ru.durnov.taco.Taco;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

@Repository
public class JdbcTacoRepository implements TacoRepository {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public JdbcTacoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Taco save(Taco taco) {
        long tacoId = saveTacoInfo(taco);
        taco.setId(tacoId);
        for (Ingredient ingredient:taco.getIngredients()){
            saveIngredientToTaco(ingredient, tacoId);
        }
        return taco;
    }

    //Как я понял, в базу заносится новое taco и возвращается его id.
    private long saveTacoInfo(Taco taco){
        taco.setCreateAt(new Date());
        PreparedStatementCreator psc = new PreparedStatementCreatorFactory("insert into Taco (name, createAt) values (?, ?)",
                Types.VARCHAR, Types.TIMESTAMP).newPreparedStatementCreator(Arrays.asList(taco.getName(),
                new Timestamp(taco.getCreateAt().getTime())));
        KeyHolder keyHolder =new GeneratedKeyHolder();
        jdbcTemplate.update(psc, keyHolder);
        return keyHolder.getKey().longValue();
    }

    //здесь все просто. Заносится в таблицу пара taco и ingredient. В методе save этот метод вызывается в цикле
    //для каждого ingredient для текущего taco
    private void saveIngredientToTaco (Ingredient ingredient, long tacoId){
        jdbcTemplate.update("insert into Taco_Ingredients (taco, ingredient) values (?, ?))", tacoId, ingredient.getId());
    }
}
