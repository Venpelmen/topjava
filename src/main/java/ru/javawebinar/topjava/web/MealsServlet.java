package ru.javawebinar.topjava.web;


import org.slf4j.Logger;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealManager;
import ru.javawebinar.topjava.model.Meal;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

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
        if (action != null) {
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
        forward(request, response);
    }

    private void forward(HttpServletRequest request, HttpServletResponse response) {
        request.setAttribute("meals", getFilteredDefault());
        try {
            request.getRequestDispatcher("/meals.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        if (request.getParameter("method") != null) {
            if (request.getParameter("method").equals("insert")) {
                mealDao.insert(new Meal(request.getParameterMap(),true));
            }
            else if(request.getParameter("method").equals("change")){
                mealDao.change(new Meal(request.getParameterMap(),false));
            }
        }
        forward(request, response);
    }

}

