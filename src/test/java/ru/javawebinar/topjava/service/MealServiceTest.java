package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER;


@ContextConfiguration({
        "classpath:spring/spring-app-test.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }


    @Autowired
    private MealService service;


    @BeforeTestMethod
    public void resetDb(){

    }
    @Test
    public void get() {
        Meal meal = service.get(MEAL_FIRST_ID, USER_ID);
        assertMatch(meal,meals.get(0));
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() {
     service.get(NOT_PRESENT_MEAL,NOT_PRESENT_USER_ID);
    }


    @Test
    public void delete() {
        Meal meal = service.get(MEAL_FIRST_ID, USER_ID);
        service.delete(MEAL_FIRST_ID, USER_ID);
        meals.remove(meal);
        assertMatch(service.getAll(USER_ID),meals);
    }

    @Test(expected = NotFoundException.class)
    public void deletedNotFound() {
        service.delete(NOT_PRESENT_MEAL,NOT_PRESENT_USER_ID);
    }


    @Test
    public void getBetweenDates() {
    }

    @Test
    public void getAll() {
        List<Meal> dbMeals = service.getAll(USER_ID);
        assertMatch(dbMeals,meals);
    }

    @Test
    public void update() {
        Meal meal = new Meal(LocalDateTime.now(), null, 1000);
        meal.setId(MEAL_FIRST_ID);
        service.update(meal, USER_ID);
        assertMatch(service.get(MEAL_FIRST_ID,USER_ID),meal);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() {
        Meal meal = new Meal(LocalDateTime.now(), null, 1000);
        service.update(meal,NOT_PRESENT_USER_ID);
    }


    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.now(), null, 10);
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        assertMatch(service.getAll(USER_ID), created);
    }
}