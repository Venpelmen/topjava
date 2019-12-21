package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.util.Collection;

@Controller
public class MealRestController {
    //Todo 4.4: конвертацию в MealTo можно делать как в слое web, так и в service (Mapping Entity->DTO: Controller or Service?)
    private int userId = SecurityUtil.authUserId();
    private final MealService service;

    public MealRestController(MealService service) {
        this.service = service;
    }

    public void delete(int id) {
       service.delete(id,userId);
    }

    public Meal get(int id) {
       return service.get(id,userId);
    }


    public Collection<Meal> getAll() {
       return service.getAll(userId);
    }

    public void update(Meal meal) {
        service.update(meal,userId);
    }

    public void create(Meal meal) {
        service.create(meal,userId);
    }

    public Collection<Meal> getAllWithFiltered() {
        return service.getAllFiltered(userId);
    }
}