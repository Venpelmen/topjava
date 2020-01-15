package ru.javawebinar.topjava.web.meal;

import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;
import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

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


    public Collection<MealTo> getAll() {
        return MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), DEFAULT_CALORIES_PER_DAY);
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }

    public void create(Meal meal) {
        checkNew(meal);
        service.create(meal, SecurityUtil.authUserId());
    }

    public Collection<MealTo> getAllWithFiltered(String startDate, String endDate, String startTime, String endTime) {
        return !startDate.equals("") && !endDate.equals("") ? service.getAllFiltered(SecurityUtil.authUserId(), LocalDate.parse(startDate), LocalDate.parse(endDate)) :
                !startTime.equals("") && !endTime.equals("") ? service.getAllFiltered(SecurityUtil.authUserId(), LocalTime.parse(startTime), LocalTime.parse(endTime)) :
                        MealsUtil.getTos(service.getAll(SecurityUtil.authUserId()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }
}