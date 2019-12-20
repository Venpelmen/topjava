package ru.javawebinar.topjava.service;

import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.Collection;
import java.util.Comparator;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundForAuthorizedUserWithId;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {

    private final MealRepository repository;

    public MealService(MealRepository repository) {
        this.repository = repository;
    }


    public void update(Meal meal, int userId) throws NotFoundException {
        try {
            checkNotFoundForAuthorizedUserWithId(repository.get(meal.getId()).getUserId() == userId, userId);
            checkNotFoundWithId(repository.save(meal), meal.getId());
        }
        catch (NullPointerException e){
            throw new NotFoundException("Not found entity with id=" + meal.getId());
        }
    }


    public void delete(int id, int userId) throws NotFoundException {
        try {
            checkNotFoundForAuthorizedUserWithId(repository.get(id).getUserId() == userId, userId);
            repository.delete(id);
        }
        catch (NullPointerException e){
            throw new NotFoundException("Not found entity with id=" + id);
        }
    }


    public Meal get(int id, int userId) throws NotFoundException {
        try {
        Meal meal  = checkNotFoundWithId(repository.get(id), id);
        checkNotFoundForAuthorizedUserWithId(meal.getUserId() == userId, userId);
        return meal;
        }
        catch (NullPointerException e){
            throw new NotFoundException("Not found entity with id=" + id);
        }
    }

    public Collection<Meal> getAll(int userId) {
        return repository.getAll().stream().sorted(new Comparator<Meal>() {
            @Override
            public int compare(Meal meal, Meal meal1) {
                return meal.getDate().compareTo(meal1.getDate());
            }
        }).collect(Collectors.toList());
    }
}