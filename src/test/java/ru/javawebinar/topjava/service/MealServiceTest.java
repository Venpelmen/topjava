package ru.javawebinar.topjava.service;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.ExternalResource;
import org.junit.rules.TestName;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;
import static ru.javawebinar.topjava.util.ValidationUtil.NOT_FOUND_ERROR_TEXT;
import static ru.javawebinar.topjava.util.ValidationUtil.WITH_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @ClassRule
    public static final ExternalResource externalResourceForClass = ExternalResourceWithTimeCalc.externalResourceForClass;
    @Rule
    public final ExternalResource externalResource = new ExternalResourceWithTimeCalc().externalResourceForMethod;
    @Rule
    public final ExpectedException thrown = ExpectedException.none();
    @Rule
    public final TestName name = new TestName();
    @Autowired
    private MealService service;

    @Test
    public void delete() {
        service.delete(MEAL1_ID, USER_ID);
        List<Meal> dbMeals = service.getAll(USER_ID);
        dbMeals.forEach(item -> item.setUser(null));
        assertMatch(dbMeals, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
    }

    @Test
    public void deleteNotFound() {

        thrown.expect(NotFoundException.class);
        thrown.expectMessage(NOT_FOUND_ERROR_TEXT + WITH_ID + NOT_PRESENT_MEAL_ID);
        service.delete(NOT_PRESENT_MEAL_ID, USER_ID);
    }

    @Test
    public void deleteNotOwn() {
        thrown.expect(NotFoundException.class);
        thrown.expectMessage(NOT_FOUND_ERROR_TEXT + WITH_ID + MEAL1_ID);
        service.delete(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = getCreated();
        Meal created = service.create(newMeal, USER_ID);
        newMeal.setId(created.getId());
        newMeal.setUser(null);
        assertMatch(newMeal, created);
        List<Meal> dbMeals = service.getAll(USER_ID);
        dbMeals.forEach(item -> item.setUser(null));
        assertMatch(dbMeals, newMeal, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
    }

    @Test
    public void get() {

        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        actual.setUser(null);
        assertMatch(actual, ADMIN_MEAL1);
    }

    @Test
    public void getNotFound() {

        thrown.expect(NotFoundException.class);
        thrown.expectMessage(NOT_FOUND_ERROR_TEXT + WITH_ID + NOT_PRESENT_MEAL_ID);
        service.get(NOT_PRESENT_MEAL_ID, USER_ID);
    }

    @Test
    public void getNotOwn() {

        thrown.expect(NotFoundException.class);
        thrown.expectMessage(NOT_FOUND_ERROR_TEXT + WITH_ID + MEAL1_ID);
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void update() {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        Meal dbMeal = service.get(MEAL1_ID, USER_ID);
        dbMeal.setUser(null);
        assertMatch(dbMeal, updated);
    }

    @Test
    public void updateNotFound() {

        thrown.expect(NotFoundException.class);
        thrown.expectMessage(NOT_FOUND_ERROR_TEXT + WITH_ID + NOT_PRESENT_MEAL_ID);
        service.update(getNotPresentMealForUpdate(), ADMIN_ID);
    }

    @Test
    public void getAll() {

        List<Meal> dbMeals = service.getAll(USER_ID);
        dbMeals.forEach(item -> item.setUser(null));
        assertMatch(dbMeals, MEALS);
    }

    @Test
    public void getBetween() {

        List<Meal> dbMeals = service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID);
        dbMeals.forEach(item -> item.setUser(null));
        assertMatch(dbMeals, MEAL3, MEAL2, MEAL1);
    }
}