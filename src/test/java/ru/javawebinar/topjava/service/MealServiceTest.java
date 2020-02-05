package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.MealTestData.MEAL_FIRST_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;


    @Test
    public void get() {
        service.get(MEAL_FIRST_ID,USER_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL_FIRST_ID,USER_ID);
    }

    @Test
    public void getBetweenDates() {
    }

    @Test
    public void getAll() {
        service.getAll(USER_ID);
    }

    @Test
    public void update() {
        Meal meal = new Meal(LocalDateTime.now(), null, 1000);
        meal.setId(MEAL_FIRST_ID);
        service.update(meal,USER_ID);
    }

    @Test
    public void create() {
        Meal meal = new Meal(LocalDateTime.now(), null, 1000);
        service.create(meal,USER_ID);
    }
}