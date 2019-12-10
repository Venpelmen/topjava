package ru.javawebinar.topjava.model;

import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.getLastId;
import static ru.javawebinar.topjava.util.MealsUtil.getMeals;

public class Meal {

    private static final Logger log = getLogger(Meal.class);
    private LocalDateTime dateTime;


    private String description;

    private int calories;

    private int id;

    public Meal(Integer id, LocalDateTime dateTime, String description, int calories) {
        this.id = id;
        this.dateTime = dateTime;
        this.description = description;
        this.calories = calories;
    }

    public Meal(Map<String, String[]> parameterMap,boolean incrementId ) {
        try {
            if(incrementId) {
                this.id = getLastId();
            }
            else {
                this.id = Integer.valueOf(parameterMap.get("id")[0]);
            }
            this.dateTime = LocalDateTime.parse(parameterMap.get("dateTime")[0]);
            this.description = parameterMap.get("description")[0];
            this.calories = Integer.parseInt(parameterMap.get("calories")[0]);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            log.error(e.getMessage());
        }
    }


    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getDescription() {
        return description;
    }

    public int getCalories() {
        return calories;
    }

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }

    public int getId() {
        return id;
    }
}
