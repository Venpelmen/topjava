package ru.javawebinar.topjava.dao;

import java.util.Map;

public interface MealManager {
    void change(Map<String, String[]> parameterMap);

    void delete(Integer id);

    void insert();
}
