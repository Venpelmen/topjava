package ru.javawebinar.topjava.dao;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.web.MealsServlet;

import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.getMeals;

public class MealDao implements MealManager {


    public MealDao() {
        //getMeals().stream().filter(mealTo ->(mealTo.getId() == Integer.parseInt(req.getParameterMap().get("id")[0]))).forEach(mealTo -> mealTo.fromMap()

    }

    @Override
    public void change(Map<String, String[]> parameterMap) {
        int id = Integer.parseInt(parameterMap.get("id")[0]);
        getMeals().set(id - 1, new Meal(parameterMap));
    }

    @Override
    public void delete(Integer id) {
        getMeals().remove(id -1);

    }

    @Override
    public void insert() {

    }
}
