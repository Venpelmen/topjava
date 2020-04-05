package ru.javawebinar.topjava.service.meal;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest {


   @Test
    public void getMealWithUser(){
        MEAL_MATCHER_WITH_USER.assertMatch(repository.getWithUser(MEAL1.getId(),USER_ID),MEAL1_WITH_USER);
    }

}
