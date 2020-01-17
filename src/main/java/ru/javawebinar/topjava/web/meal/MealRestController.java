package ru.javawebinar.topjava.web.meal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;

@Controller
public class MealRestController {

    private final MealService service;

    @Autowired
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
        return service.getAll(SecurityUtil.authUserId());
    }

    public void update(Meal meal, int id) {
        assureIdConsistent(meal, id);
        service.update(meal, SecurityUtil.authUserId());
    }

    public Collection<MealTo> getAllWithFiltered(LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {

        return service.getAllFiltered(SecurityUtil.authUserId(), startDate, endDate, startTime, endTime);
    }
}