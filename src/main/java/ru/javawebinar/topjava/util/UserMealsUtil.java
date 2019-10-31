package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.groupingBy;
import static ru.javawebinar.topjava.util.TimeUtil.isBetween;

public class UserMealsUtil {
    public static void main(String[] args) {
        List < UserMeal > mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of
                        (2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of
                        (2015, Month.MAY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of
                        (2015, Month.MAY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of
                        (2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of
                        (2015, Month.MAY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of
                        (2015, Month.MAY, 31, 20, 0), "Ужин", 510)
        );
        getFilteredWithExceeded
                (mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        getFilteredWithExceededWithStream
                (mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        //        .toLocalDate();
        //        .toLocalTime();
    }

    private static List <UserMealWithExceed> getFilteredWithExceeded
            (List <UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        Map <LocalDate,Integer> sumCaloriesForPerDay = new HashMap < > ();

        List <UserMealWithExceed> userMealWithExceed = new ArrayList < > ();
        mealList.forEach(userMeal -> {
            if (isBetween(userMeal.getLocalTime(), startTime, endTime)) {
                userMealWithExceed.add(new UserMealWithExceed
                        (userMeal.getDateTime(), userMeal.getDescription(), userMeal.getCalories()));
            }
            //Суммировние каллорий по ключу даты
            sumCaloriesForPerDay.merge(userMeal.getLocalDate(), userMeal.getCalories(), Integer::sum);
        });

        //Создание результирующего листа с информацией о превышении и количеством каллорий
        for (UserMealWithExceed item: userMealWithExceed) {
            item.setCalories(sumCaloriesForPerDay.get(item.getLocalDate()));
            if (item.getCalories() > caloriesPerDay) {
                item.setExceed(true);
            } else
                item.setExceed(false);
        }
        return userMealWithExceed;
    }

    private static List < UserMealWithExceed > getFilteredWithExceededWithStream
            (List < UserMeal > mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        //Суммировние каллорий по ключу даты
        Map < LocalDate, Integer > sumCaloriesForPerDay = mealList.stream().
                collect(groupingBy(UserMeal::getLocalDate, Collectors.summingInt(UserMeal::getCalories)));

        return mealList.stream().filter(
                item -> isBetween(item.getLocalTime(), startTime, endTime)).
                map(l -> new UserMealWithExceed
                        (l.getDateTime(), l.getDescription(), sumCaloriesForPerDay.get(l.getLocalDate()),
                        sumCaloriesForPerDay.get
                                (l.getLocalDate()) > caloriesPerDay)).collect(Collectors.toList());
    }
}