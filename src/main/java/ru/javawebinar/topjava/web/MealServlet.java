package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;
import ru.javawebinar.topjava.web.meal.MealRestController;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.MealsUtil.DEFAULT_CALORIES_PER_DAY;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private MealRestController mealRestController;
    private ClassPathXmlApplicationContext classPathXmlApplicationContext;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        classPathXmlApplicationContext = new ClassPathXmlApplicationContext("spring/spring-app.xml");
        mealRestController = classPathXmlApplicationContext.getBean(MealRestController.class);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        List<MealTo> meals;
        if (request.getParameter("action") != null) {
            meals = filter(request, response);
            request.setAttribute("meals",
                    meals);
            if (meals == null) {
                meals = MealsUtil.getTos(mealRestController.getAll(), DEFAULT_CALORIES_PER_DAY);
            }
            request.setAttribute("meals",meals);
            request.getRequestDispatcher("/meals.jsp").forward(request, response);

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

    private List<MealTo> filter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<MealTo> filteredTos = null;
        boolean isFilterWithDateRequest = !request.getParameter("startDateTime").equals("") & !request.getParameter("endDateTime").equals("");
        boolean isFilterWithTimeRequest = !request.getParameter("startTime").equals("") & !request.getParameter("endTime").equals("");
        if (isFilterWithDateRequest) {
            LocalDate startDate = LocalDate.parse(request.getParameter("startDateTime"));
            LocalDate endDate = LocalDate.parse(request.getParameter("endDateTime"));
            filteredTos = MealsUtil.getFilteredTos(mealRestController.getAllWithFiltered(), DEFAULT_CALORIES_PER_DAY, startDate, endDate);

        } else if (isFilterWithTimeRequest) {
            LocalTime startTime = LocalTime.parse(request.getParameter("startTime"));
            LocalTime endTime = LocalTime.parse(request.getParameter("endTime"));
            filteredTos = MealsUtil.getFilteredTos(mealRestController.getAllWithFiltered(), DEFAULT_CALORIES_PER_DAY, startTime, endTime);
        }
       /* else {
            filteredTos =   MealsUtil.getTos(mealRestController.getAll(), DEFAULT_CALORIES_PER_DAY);
        }*/
        return filteredTos;

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


    @Override
    public void destroy() {
        super.destroy();
        classPathXmlApplicationContext.close();

    }
}
