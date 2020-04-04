package ru.javawebinar.topjava;

import org.slf4j.Logger;
import org.springframework.test.context.ActiveProfilesResolver;

import static org.slf4j.LoggerFactory.getLogger;

//http://stackoverflow.com/questions/23871255/spring-profiles-simple-example-of-activeprofilesresolver
public class ActiveDbProfileResolver implements ActiveProfilesResolver {
    private static final Logger log = getLogger(ActiveDbProfileResolver.class);

    @Override
    public String[] resolve(Class<?> aClass) {
        String[] dbAndRepoImpl = Profiles.getActiveDbProfileAndRepoIml();
        log.info("______________________Db is " + dbAndRepoImpl[0] + " repository implementation is " + dbAndRepoImpl[1]+"______________________");
        return dbAndRepoImpl;
    }
}