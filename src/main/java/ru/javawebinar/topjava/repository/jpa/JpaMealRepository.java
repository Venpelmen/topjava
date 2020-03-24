package ru.javawebinar.topjava.repository.jpa;

import org.springframework.beans.BeanUtils;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepository implements MealRepository {

    @PersistenceContext
    protected EntityManager em;


    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (meal.isNew()) {
            meal.setUser(em.getReference(User.class, userId));
            em.persist(meal);
            return meal;
        } else {
            Meal existingMealOrNull = this.get(meal.getId(), userId);
            if (Objects.nonNull(existingMealOrNull)) {
                BeanUtils.copyProperties(meal, existingMealOrNull);
                existingMealOrNull.setUser(em.find(User.class, userId));
            }
            return existingMealOrNull;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE).setParameter(1, id).setParameter(2, userId).executeUpdate() != 0;
    }

    @Override
    @Transactional
    //Почему-то не открывает транзакцию аннотированную в классе...
    public Meal get(int id, int userId) {
        TypedQuery<Meal> typedQuery = em.createNamedQuery(Meal.GET, Meal.class).setParameter(1, id).setParameter(2, userId);
        return DataAccessUtils.singleResult(typedQuery.getResultList());

    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.GET_ALL, Meal.class).setParameter(1, userId).getResultList();
    }

    @Override
    @Transactional
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter(1, userId).setParameter(2, startDate).setParameter(3, endDate)
                .getResultList();
    }
}