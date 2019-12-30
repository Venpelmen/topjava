package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
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
            checkNotFoundWithId(repository.save(meal, userId), userId);
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
        try {
            return repository.getAll(userId);
        } catch (NullPointerException e) {
            throw new NotFoundException("Not found map with userId=" + userId);
        }
    }

    public Collection<Meal> getAllSorted(int userId) {
        try {
        return getAll(userId).stream().
                sorted(Comparator.comparing(Meal::getDate)).collect(Collectors.toList());
        } catch (NullPointerException e) {
            throw new NotFoundException("Not found map with userId=" + userId);
        }
    }
}