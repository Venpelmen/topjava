package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealCrud {

    void delete(int id);

    void create(Meal meal);

    void update(int id, Meal meal);

    Meal read(int id);

    List<Meal> getAll();
}
