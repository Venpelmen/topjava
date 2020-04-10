package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    Meal getByIdAndUserId(int id, int userId);

    @Transactional
    int deleteByIdAndUserId(int id, int userId);

    List<Meal> findAllByUserIdOrderByDateTimeDesc(int userId);

    @Query("select m from Meal m where m.user.id = ?1 and m.dateTime >=?2 and m.dateTime <?3 order by m.dateTime desc")
    List<Meal> getBetweenHalfOpen(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    @Query("select  m from Meal m LEFT JOIN FETCH m.user u where m.id = ?1 and u.id = ?2")
    Meal getWithUser(int id, int userId);


}
