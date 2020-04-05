package ru.javawebinar.topjava.service.user;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.UserTestData.*;


@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void userWithMeal() {
        USER.setMeals(MEALS);
        USER_MATCHER_WITH_MEALS.recursiveAssertMatch(repository.getWithMeal(USER_ID), USER);
    }

}
