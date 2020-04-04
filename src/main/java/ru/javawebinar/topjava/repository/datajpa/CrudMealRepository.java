package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {


   // List<Meal> findAllByDateTimeBetweenAndUserIdOrderByDateTimeDesc(LocalDateTime startDateTime, LocalDateTime endDateTime, int userId);

    Meal getByIdAndUserId(int id, int userId);

    @Transactional
    int deleteByIdAndUserId(int id, int userId);

    List<Meal> findAllByUserIdOrderByDateTimeDesc(int userId);
//AndGreaterThanEqual
    List<Meal> findAllByUserIdAndDateTimeLessThanEqualOrderByDateTimeDesc(int userId, LocalDateTime startDateTime);

    List<Meal> findAllByUserIdAndDateTimeLessThanEqualAndDateTimeGreaterThanEqualOrderByDateTimeDesc(int userId, LocalDateTime startDateTime,LocalDateTime endDateTime);

    List<Meal> findAllByUserIdAndDateTimeGreaterThanAndDateTimeLessThanOrderByDateTimeDesc(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime);
    List<Meal> findAllByUserIdAndDateTimeBetweenOrderByDateTimeDesc(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime);

}
