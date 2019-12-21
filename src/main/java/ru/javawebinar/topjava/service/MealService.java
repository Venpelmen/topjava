package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }

    //Реализация с NotFoundException при NullPointerException грубовата, но работает
    public void update(Meal meal, int userId) throws NotFoundException {
        try {
            checkNotFoundWithId(repository.save(meal,userId), userId);
        } catch (NullPointerException e) {
            throw new NotFoundException("Not found entity with id=" + meal.getId());
        }
    }


    public void create(Meal meal, int userId) throws NotFoundException {
        repository.save(meal, userId);
    }


    public void delete(int id, int userId) throws NotFoundException {
        try {
            checkNotFoundWithId(repository.delete(id, userId), id);
        } catch (NullPointerException e) {
            throw new NotFoundException("Not found entity with id=" + id);
        }
    }


    public Meal get(int id, int userId) throws NotFoundException {
        try {
            return checkNotFoundWithId(repository.get(id, userId), id);
        } catch (NullPointerException e) {
            throw new NotFoundException("Not found entity with id=" + id);
        }
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll(userId).stream().
                sorted(Comparator.comparing(Meal::getDate)).collect(Collectors.toList());
    }

    public Collection<Meal> getAllFiltered(int userId) {
        return repository.getAll(userId).stream().
                sorted(Comparator.comparing(Meal::getDate).reversed()).collect(Collectors.toList());
    }
}