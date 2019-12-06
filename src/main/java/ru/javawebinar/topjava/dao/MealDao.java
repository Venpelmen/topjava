package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.getMeals;

public class MealDao implements MealManager{


    public MealDao() {
        //getMeals().stream().filter(mealTo ->(mealTo.getId() == Integer.parseInt(req.getParameterMap().get("id")[0]))).forEach(mealTo -> mealTo.fromMap()

    }

    @Override
    public void changeRecord() {

    }
}
