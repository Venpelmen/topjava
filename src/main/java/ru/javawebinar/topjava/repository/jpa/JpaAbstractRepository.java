package ru.javawebinar.topjava.repository.jpa;

import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.AbstractBaseEntity;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class JpaAbstractRepository<T extends AbstractBaseEntity> {

    @PersistenceContext
    protected EntityManager em;

    @Transactional
    public T save(T user) {
        if (user.isNew()) {
            em.persist(user);
            return user;
        } else {
            return em.merge(user);
        }
    }
}
