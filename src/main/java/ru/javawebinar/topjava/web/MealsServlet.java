package ru.javawebinar.topjava.web;


import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealCrud;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.getFilteredDefault;

public class MealsServlet extends HttpServlet {
    private static final Logger log = getLogger(MealsServlet.class);
    private MealCrud mealDao = new MealDao();


    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to servlet");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action != null) {
            int id = Integer.parseInt(request.getParameter("id"));
            if (action.equalsIgnoreCase("delete")) {
                mealDao.delete(id);
                response.sendRedirect("meals");
            }
            if (action.equalsIgnoreCase("forwardToUpdate")) {
                forwardToUpdate(request, response, mealDao.read(id));
            }
        } else
            forward(request, response);
    }


    private void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("meals", getFilteredDefault(mealDao.getAll()));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    private void forwardToUpdate(HttpServletRequest request, HttpServletResponse response, Meal meal) throws ServletException, IOException {
        request.setAttribute("meal", meal);
        request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        final Map<String, String[]> parameterMap = request.getParameterMap();
        if (request.getParameter("method").equals("create")) {
            mealDao.create(createMeal(parameterMap));
            forward(request, response);
        } else if (request.getParameter("method").equals("update")) {
            int id = Integer.parseInt(parameterMap.get("id")[0]);
            mealDao.update(id, createMeal(parameterMap));
            forward(request, response);
        }
    }

    private Meal createMeal(Map<String, String[]> parameterMap) {
        LocalDateTime localDateTime = LocalDateTime.parse(parameterMap.get("dateTime")[0]);
        String description = parameterMap.get("description")[0];
        int calories = Integer.parseInt(parameterMap.get("calories")[0]);
        return new Meal(localDateTime, description, calories);
    }
}

