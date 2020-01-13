package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

@Controller
public class MealRestController {


    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public void delete(int id) {
        service.delete(id, SecurityUtil.authUserId());
    }

    public Meal get(int id) {
        return service.get(id, SecurityUtil.authUserId());
    }


    public Collection<Meal> getAll() {
        return service.getAll(SecurityUtil.authUserId());
    }

    public void update(Meal meal) {
        service.update(meal, SecurityUtil.authUserId());
    }

    public void create(Meal meal) {
        service.create(meal, SecurityUtil.authUserId());
    }

    public <T extends Comparable<T>> Collection<Meal>  getAllWithFiltered(T start, T end) {
        return service.getAllFiltered(SecurityUtil.authUserId(),start,end);
    }
}