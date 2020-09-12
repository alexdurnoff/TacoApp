package ru.durnov.taco.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.durnov.taco.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class JdbcIngredientRepository implements IngredientRepository {
    private JdbcTemplate jdbc;

    @Autowired
    public JdbcIngredientRepository(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public Iterable<Ingredient> findAll() {
        return jdbc.query("select id, name, type from Ingredient", this::mapRowToIngredient);
    }

    @Override
    public Ingredient findOne(String id) {
        return jdbc.queryForObject("select id, name, type from Ingredient where id=?", this::mapRowToIngredient, id);
    }

    @Override
    public Ingredient save(Ingredient ingredient) {
        jdbc.update("insert into Ingredient (id, name, type) values (?, ?, ?)", ingredient.getId(),
                ingredient.getName(), ingredient.getType().toString());
        return ingredient;
    }


    //Как я понял, этот метод показывает, как вынуть Ingredient из ResultSet. Используется для получения объекта iterable
    //в запросе jdbc. Как я понял, RowMapper<Type> - это объетк у которого есть метод mapRow,
    //который возвращает объект типа Type из ResultSet из строки под номером rowNumber. А здесь мы вместо RowMapper
    //подсунули ссылку на метод, который делает то же самое.
    public Ingredient mapRowToIngredient(ResultSet resultSet, int rownumber) throws SQLException {
        return new Ingredient(resultSet.getString("id"), resultSet.getString("name"),
                Ingredient.Type.valueOf(resultSet.getString("type")));
    }
}
