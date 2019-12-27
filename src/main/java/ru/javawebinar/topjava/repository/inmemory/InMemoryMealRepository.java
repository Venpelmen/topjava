package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

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
        if (meal.isNew() & repository.get(userId) != null) {
            meal.setId(counter.incrementAndGet());
            repository.get(userId).put(meal.getId(), meal);
            return meal;
        } else {
            meal.setId(counter.incrementAndGet());
            repository.put(userId, new ConcurrentHashMap<>()).put(meal.getId(), meal);
        }
        // treat case: update, but not present in storage
        return repository.get(userId).computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return repository.get(userId).remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {
        return repository.get(userId).get(id);
    }


    @Override
    public Collection<Meal> getAll(int userId) {
        return repository.get(userId).values().stream().
                sorted(Comparator.comparing(Meal::getDate).reversed()).collect(Collectors.toList());
    }


   /* @Override
    public List<MealTo> getAllWithFiltered(int userId, LocalDate startDate, LocalDate endDate){
       return MealsUtil.getFilteredTos(getAll(userId), DEFAULT_CALORIES_PER_DAY, startDate, endDate);
    }*/

}

