package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }


    @Autowired
    private MealService service;


    @Test
    public void get() {
        Meal meal = service.get(MEAL_FIRST_ID, USER_ID);
        assertMatch(meal, FIRST_MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
        service.get(NOT_PRESENT_MEAL, ADMIN_ID);
    }


    @Test
    public void delete() {
        service.delete(MEAL_FIRST_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), SECOND_MEAL, THIRD_MEAL, FOURTH_MEAL, FIFTH_MEAL, SIXTH_MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() {
        service.delete(NOT_PRESENT_MEAL, ADMIN_ID);
    }


    @Test
    public void getBetweenDates() {
        assertMatch(service.getBetweenDates(LocalDate.MIN, LocalDate.of(2015, 5, 30), USER_ID), FIRST_MEAL, SECOND_MEAL, THIRD_MEAL);
    }

    @Test
    public void getAll() {
        List<Meal> dbMeals = service.getAll(USER_ID);
        assertMatch(dbMeals, FIFTH_MEAL, SECOND_MEAL, THIRD_MEAL, FOURTH_MEAL, FIFTH_MEAL, SIXTH_MEAL);
    }

    @Test
    public void update() {
        Meal meal = new Meal(LocalDateTime.now(), "Тортик", 1000);
        meal.setId(MEAL_FIRST_ID);
        service.update(meal, USER_ID);
        assertMatch(service.get(MEAL_FIRST_ID, USER_ID), meal);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal meal = new Meal();
        meal.setId(MEAL_FIRST_ID);
        service.update(meal, ADMIN_ID);
    }


    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.now(), "Тортик", 10);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID).get(0), created);
    }
}