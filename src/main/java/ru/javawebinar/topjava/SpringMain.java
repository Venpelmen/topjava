package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.web.SecurityUtil;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    private static List<String> operations = Arrays.asList("Get", "Update", "Delete");
    public static MealRestController mealRestController;

    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            UserRepository userBean = appCtx.getBean(UserRepository.class);
            MealRepository mealRepository = appCtx.getBean(MealRepository.class);
            //Проверка существующей записи, которая принадлежит авторизованному пользователю
            //Check step 3.3
            System.out.println(mealRepository.getAll(SecurityUtil.authUserId()));
            userBean.getByEmail("email@mail.ru");
          checkSomeIdOnCrudOperation(1,mealRestController);
            checkSomeIdOnCrudOperation(127,mealRestController);
            checkSomeIdOnCrudOperation(2,mealRestController);

        }
    }


    private static void checkSomeIdOnCrudOperation(int id, MealRestController bean) {
        try {
            System.out.println(bean.get(id));
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        try {
            Meal meal =  new Meal(id, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
          //  meal.setUserId(1);
            bean.update(meal);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        try {
            bean.delete(id);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
}
