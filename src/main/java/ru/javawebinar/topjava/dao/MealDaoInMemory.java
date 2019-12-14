package ru.javawebinar.topjava.dao;


import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoInMemory implements MealCrud {
    private static AtomicInteger lastId = new AtomicInteger(0);
    private static ConcurrentMap<Integer, Meal> meals = new ConcurrentHashMap<Integer, Meal>() {
        {

            put(lastId.incrementAndGet(), new Meal(lastId.get(), LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
            put(lastId.incrementAndGet(), new Meal(lastId.get(), LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
            put(lastId.incrementAndGet(), new Meal(lastId.get(), LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
            put(lastId.incrementAndGet(), new Meal(lastId.get(), LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
            put(lastId.incrementAndGet(), new Meal(lastId.get(), LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
            put(lastId.incrementAndGet(), new Meal(lastId.get(), LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        }
    };


    @Override
    public void delete(int id) {
        meals.remove(id);
    }

    @Override
    public void create(Meal meal) {
        final int id = lastId.incrementAndGet();
        meal.setId(id);
        meals.put(id, meal);
    }

    @Override
    public void update(int id, Meal meal) {
        meal.setId(id);
        meals.replace(id, meal);
    }

    @Override
    public Meal read(int id) {
        return meals.get(id);
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(meals.values());
    }


}
