package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_FIRST_ID = START_SEQ + 2;
    public static final int NOT_PRESENT_MEAL = 1;

    public static final Meal FIRST_MEAL = new Meal(MEAL_FIRST_ID, LocalDateTime.of(2015, Month.MAY, 30, 9, 30), "Тортик", 500);
    public static final Meal SECOND_MEAL = new Meal(100003, LocalDateTime.of(2015, Month.MAY, 30, 10, 30), "Тортик", 1000);
    public static final Meal THIRD_MEAL = new Meal(100004, LocalDateTime.of(2015, Month.MAY, 30, 11, 30), "Тортик", 500);
    public static final Meal FOURTH_MEAL = new Meal(100005, LocalDateTime.of(2015, Month.MAY, 31, 9, 30), "Тортик", 1000);
    public static final Meal FIFTH_MEAL = new Meal(100006, LocalDateTime.of(2015, Month.MAY, 31, 10, 30), "Тортик", 500);
    public static final Meal SIXTH_MEAL = new Meal(100007, LocalDateTime.of(2015, Month.MAY, 31, 11, 30), "Тортик", 510);


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingDefaultComparator().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertThat(actual).usingDefaultComparator().contains(expected);
    }
}




