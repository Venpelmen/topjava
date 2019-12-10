package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.util.MealsUtil.getMeals;

public class MealDao implements MealManager {


    public MealDao() {


    }

    @Override
    public void delete(Integer id) {
        getMeals().remove(getMeals().stream().filter(meal -> meal.getId() == id).findFirst().orElse(null));

    }

    @Override
    public void insert(Meal meal) {
        getMeals().add(meal);
    }

    @Override
    public void change(Meal meal) {
        delete(meal.getId());
        getMeals().add(meal);
    }
}
