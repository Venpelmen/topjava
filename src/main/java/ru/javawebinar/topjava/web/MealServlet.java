package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.inmemory.InMemoryMealRepository;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

import static ru.javawebinar.topjava.SpringMain.mealRestController;
import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private final MealRestController mealRestController = new ClassPathXmlApplicationContext("spring/spring-app.xml").getBean(MealRestController.class);


    //Todo  учесть, что когда будем работать через Spring MVC, MealServlet удалим, те вся логика должна быть в контроллере. Нипонил
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        if (request.getParameter("action") != null) {
            filter(request, response);
        } else {
            String id = request.getParameter("id");
            Meal meal = new Meal(id.isEmpty() ? null : Integer.valueOf(id),
                    LocalDateTime.parse(request.getParameter("dateTime")),
                    request.getParameter("description"),
                    Integer.parseInt(request.getParameter("calories")));

            log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
            mealRestController.create(meal);
            response.sendRedirect("meals");
        }
    }

    private void filter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (!request.getParameter("startDateTime").equals("") & !request.getParameter("endDateTime").equals("")) {
            LocalDateTime startDateTime = LocalDateTime.parse(request.getParameter("startDateTime"));
            LocalDateTime endDateTime = LocalDateTime.parse(request.getParameter("endDateTime"));
            request.setAttribute("meals",
                    MealsUtil.getFilteredTos(mealRestController.getAllWithFiltered(), DEFAULT_CALORIES_PER_DAY, startDateTime, endDateTime));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
            return;
        } else if (!request.getParameter("startTime").equals("") & !request.getParameter("endTime").equals("")) {
            LocalTime startDateTime = LocalTime.parse(request.getParameter("startTime"));
            LocalTime endDateTime = LocalTime.parse(request.getParameter("endTime"));
            request.setAttribute("meals",
                    MealsUtil.getFilteredTos(mealRestController.getAllWithFiltered(), DEFAULT_CALORIES_PER_DAY, startDateTime, endDateTime));
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        switch (action == null ? "all" : action) {
            case "resetFilter":
                request.setAttribute("meals",
                        MealsUtil.getTos(mealRestController.getAll(), DEFAULT_CALORIES_PER_DAY));
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
            case "delete":
                int id = getId(request);
                log.info("Delete {}", id);
                mealRestController.delete(id);
                response.sendRedirect("meals");
                break;
            case "create":
            case "update":
                final Meal meal = "create".equals(action) ?
                        new Meal(LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), "", 1000) :
                        mealRestController.get(getId(request));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
                break;
            case "all":
            default:
                log.info("getAll");
                try {
                    request.setAttribute("meals",
                            MealsUtil.getTos(mealRestController.getAll(), DEFAULT_CALORIES_PER_DAY));
                } catch (NotFoundException e) {
                    System.out.println("Записей еще нет ".concat(e.getMessage()));
                }
                request.getRequestDispatcher("/meals.jsp").forward(request, response);
                break;
        }
    }

    private int getId(HttpServletRequest request) {
        String paramId = Objects.requireNonNull(request.getParameter("id"));
        return Integer.parseInt(paramId);
    }


}
