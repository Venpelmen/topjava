package ru.javawebinar.topjava.repository.jpa;

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
            if (Objects.nonNull(get(meal.getId(), userId))) {
                meal.setUser(em.getReference(User.class, userId));
                em.merge(meal);
            } else {
                meal = null;
            }
            return meal;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {
        return em.createNamedQuery(Meal.DELETE).setParameter(1, id).setParameter(2, userId).executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {
        TypedQuery<Meal> typedQuery = em.createNamedQuery(Meal.GET, Meal.class).setParameter(1, id).setParameter(2, userId);
        return DataAccessUtils.singleResult(typedQuery.getResultList());

    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.GET_ALL, Meal.class).setParameter(1, userId).getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.GET_BETWEEN, Meal.class)
                .setParameter(1, userId).setParameter(2, startDate).setParameter(3, endDate)
                .getResultList();
    }
}