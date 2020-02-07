package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_FIRST_ID = START_SEQ + 2;
    public static final int  NOT_PRESENT_MEAL = 1;


    public static List<Meal> meals = new ArrayList<Meal>() {
        {
            add(new Meal(100007,LocalDateTime.of(2015, Month.MAY, 31, 11, 0), "", 510));
            add(new Meal(100006,LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "", 500));
            add(new Meal(100005,LocalDateTime.of(2015, Month.MAY, 31, 9, 0), "", 1000));
            add(new Meal(100004,LocalDateTime.of(2015, Month.MAY, 30, 11, 0), "", 500));
            add(new Meal(100003,LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "", 1000));
            add(new Meal(100002,LocalDateTime.of(2015, Month.MAY, 30, 9, 0), "", 500));
        }
    };


    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).isEqualToIgnoringGivenFields(expected, "userId");
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}




