package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    private static List<String> operations = Arrays.asList("Get", "Update", "Delete");

    public static void main(String[] args) {
        // java 7 automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ROLE_ADMIN));
            MealRestController bean = appCtx.getBean(MealRestController.class);
            //Проверка существующей записи, которая принадлежит авторизованному пользователю
   /*         checkSomeIdOnCrudOperation(1,bean);
            checkSomeIdOnCrudOperation(127,bean);*/
            checkSomeIdOnCrudOperation(2,bean);

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
