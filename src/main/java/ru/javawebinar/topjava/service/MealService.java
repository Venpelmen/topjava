package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealService {


    private final MealRepository repository;

    @Autowired
    public MealService(MealRepository repository) {
        this.repository = repository;
    }


    public void update(Meal meal, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.save(meal, userId), userId);
    }


    public void create(Meal meal, int userId) {
        repository.save(meal, userId);
    }


    public void delete(int id, int userId) throws NotFoundException {
        checkNotFoundWithId(repository.delete(id, userId), id);

    }

    public Meal get(int id, int userId) throws NotFoundException {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    public Collection<MealTo> getAll(int userId) {
        return MealsUtil.getTos(repository.getAll(userId), DEFAULT_CALORIES_PER_DAY);
    }

    public Collection<MealTo> getAllFiltered(int userId, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        Collection<Meal> allWithFiltered =
                startDate == LocalDate.MIN && endDate == LocalDate.MAX ?
                        repository.getAllWithFiltered(userId, startTime, endTime) :
                        repository.getAllWithFiltered(userId, LocalDateTime.of(startDate, startTime), LocalDateTime.of(endDate, endTime));
        return MealsUtil.getTos(allWithFiltered, DEFAULT_CALORIES_PER_DAY);
    }
}