package ru.javawebinar.topjava.web;


import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.slf4j.LoggerFactory.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.getFilteredDefault;

public class MealsServlet extends HttpServlet {
    private static final Logger log = getLogger(MealsServlet.class);

    private MealManager mealDao;

    public MealsServlet() {
        super();
        mealDao = new MealDao();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to servlet");
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        String forward = null;
        if (action != null) {
            if (action.equalsIgnoreCase("insert")) {
                forward = "/createMeal.jsp"
                mealDao.insert();
            }

            if (action.equalsIgnoreCase("delete")) {
                Integer id = null;
                try {
                    id = Integer.parseInt(request.getParameter("id"));
                } catch (NumberFormatException e) {
                    log.error(e.getMessage());
                }
                if (id != null) {
                    mealDao.delete(id);
                }
            }
        }
        if(forward == null) {
            request.setAttribute("meals", getFilteredDefault());
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        }
        else


    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        mealDao.change(request.getParameterMap());
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("meals", getFilteredDefault());
        request.getRequestDispatcher("/meals.jsp").forward(request, response);


    }

}

