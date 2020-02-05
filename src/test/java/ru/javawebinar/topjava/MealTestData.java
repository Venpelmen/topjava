package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;

import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int MEAL_FIRST_ID = START_SEQ;

   static List<Meal> meals = new ArrayList<Meal>(){
       {
           add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Тортик", 500));
            add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), null, 1000));
            add(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), null, 500));
            add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), null, 1000));
            add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), null, 500));
            add(new Meal(LocalDateTime.of(2015, Month.MAY, 31, 14, 0), null, 510));
        }
    };



}
