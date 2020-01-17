package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        repository.computeIfAbsent(1, integer -> new ConcurrentHashMap<>());
        MealsUtil.MEALS.forEach(item -> save(item, 1));
    }

    @Override
    public Meal save(Meal meal, int userId) {
        repository.computeIfAbsent(userId, user -> new ConcurrentHashMap<>());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but not present in storage
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, Meal> mealMap = repository.getOrDefault(userId,new ConcurrentHashMap<>());
        return mealMap.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.getOrDefault(userId, new ConcurrentHashMap<>()).get(id);
    }

    @Override
    public Collection<Meal> getAll(int userId) {
        Map<Integer, Meal> repositoryOrMap = repository.getOrDefault(userId, new ConcurrentHashMap<>());
        return  repositoryOrMap.values().stream().
                sorted(Comparator.comparing(Meal::getDate).reversed()).collect(Collectors.toList());
    }


    public Collection<Meal> getAllWithFiltered(int userId, LocalDate start, LocalDate end) {
        return MealsUtil.getFiltered(getAll(userId), start, end);
    }

    public Collection<Meal> getAllWithFiltered(int userId, LocalTime start, LocalTime end) {
        return MealsUtil.getFiltered(getAll(userId), start, end);
    }

}

