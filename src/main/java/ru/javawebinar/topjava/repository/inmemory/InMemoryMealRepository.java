package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {   //Сказано использовать SecurityUtil.authUserId() только в слое Web. Надеюсь здесь это не ошибка,
        // с таким же успехом здесь может быть id = 1. Скорее всего, требовалось чтобы мы не использовали метод в сервисе
        repository.computeIfAbsent(SecurityUtil.authUserId(), integer -> new ConcurrentHashMap<Integer, Meal>());
        MealsUtil.MEALS.forEach(item -> save(item,1));
    }


    @Override
    public Meal save(Meal meal,int userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            Map<Integer, Meal> mealItem = new ConcurrentHashMap<>();
            //Если пользователя нет в репо то мы упадем...
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.get(userId).get(id);
    }


    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.get(userId).values().stream().
                sorted(Comparator.comparing(Meal::getDate)).collect(Collectors.toCollection(ArrayList::new));
    }

}

