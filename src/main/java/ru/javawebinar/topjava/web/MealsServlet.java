package ru.javawebinar.topjava.web;


import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealCrud;
import ru.javawebinar.topjava.dao.MealDaoInMemory;
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
    private MealCrud mealDao;

    @Override
    public void init() throws ServletException {
        super.init();
        mealDao = new MealDaoInMemory();
    }

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
                request.setAttribute("meal", mealDao.read(id));
                request.getRequestDispatcher("/editMeal.jsp").forward(request, response);
            }
        } else
            forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        Meal meal = createMeal(request);
        if (request.getParameter("method").equals("create")) {
            mealDao.create(meal);
        } else if (request.getParameter("method").equals("update")) {
            int id = Integer.parseInt(request.getParameter("id"));
            mealDao.update(id, meal);
        }
        forward(request, response);
    }

    private Meal createMeal(HttpServletRequest request) {
        LocalDateTime localDateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        return new Meal(localDateTime, description, calories);
    }

    private void forward(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setAttribute("meals", getFilteredDefault(mealDao.getAll()));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }
}

