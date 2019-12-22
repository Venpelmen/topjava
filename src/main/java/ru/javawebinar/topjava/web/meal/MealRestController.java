package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

@Controller
public class MealRestController {
    //Todo 4.4: конвертацию в MealTo можно делать как в слое web, так и в service (Mapping Entity->DTO: Controller or Service?)

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

    public Collection<Meal> getAllWithFiltered() {
        return service.getAllSorted(SecurityUtil.authUserId());
    }
}