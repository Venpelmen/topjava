package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

public interface MealManager {

    void delete(Integer id);

    void insert(Meal meal);

    void change(Meal meal);
}
