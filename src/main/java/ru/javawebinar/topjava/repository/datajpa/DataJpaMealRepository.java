package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
public class DataJpaMealRepository implements MealRepository {

    @Autowired
    private CrudMealRepository crudRepository;

    //В корне неправильный подход
    @Autowired
    private CrudUserRepository crudUserRepository;

    @Override
    public Meal save(Meal meal, int userId) {
        meal.setUser(crudUserRepository.getOne(userId));
        if (!meal.isNew()) {
                Meal dbMeal = crudRepository.getByIdAndUserId(meal.getId(), userId);
                if (Objects.isNull(dbMeal)) {
                return null;
            }
        }
        return crudRepository.save(meal);
    }

    @Override
    public Meal getWithUser(int id,int userId) {
       return crudRepository.getWithUser(id,userId);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.deleteByIdAndUserId(id, userId) != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.getByIdAndUserId(id, userId);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAllByUserIdOrderByDateTimeDesc(userId);
    }

    @Override
    public List<Meal> getBetweenHalfOpen(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId) {
        return crudRepository.findAllByUserIdAndDateTimeGreaterThanAndDateTimeLessThanOrderByDateTimeDesc(userId, startDateTime, endDateTime);
        //   return crudRepository.findAllByUserIdAndDateTimeBetweenOrderByDateTimeDesc(userId,startDateTime,endDateTime);
    }
}
