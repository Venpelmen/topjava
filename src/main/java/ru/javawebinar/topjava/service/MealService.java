package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
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


    public void update(Meal meal) throws NotFoundException {
        checkNotFoundWithId(repository.save(meal), meal.getId());
    }


    public void delete(int id) throws NotFoundException
    {checkNotFoundWithId(repository.delete(id), id);
    }


    public Meal get(int id) throws NotFoundException  {
        return checkNotFoundWithId(repository.get(id), id);
    }

    public Collection<Meal> getAll() {
       return repository.getAll().stream().sorted(new Comparator<Meal>() {
            @Override
            public int compare(Meal meal, Meal meal1) {
               return meal.getDate().compareTo(meal1.getDate());
            }
        }).collect(Collectors.toList());
    }
}