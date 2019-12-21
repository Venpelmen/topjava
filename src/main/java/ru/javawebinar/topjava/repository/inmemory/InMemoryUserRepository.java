package ru.javawebinar.topjava.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryUserRepository implements UserRepository {
    private Map<Integer, User> repository = new ConcurrentHashMap<Integer, User>();
    private AtomicInteger counter = new AtomicInteger(0);
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepository.class);
    private static List<User> users = new ArrayList<User>() {
        {
            add(new User(null, "FARID", "email@mail.ru", "password", Role.ROLE_ADMIN));
            add(new User(null, "George", "email@mail.ru", "password", Role.ROLE_ADMIN));
            add(new User(null, "Elisa", "email@mail.ru", "password", Role.ROLE_ADMIN));
            add(new User(null, "Donald", "email@mail.ru", "password", Role.ROLE_ADMIN));
            add(new User(null, "Cindy", "email@mail.ru", "password", Role.ROLE_ADMIN));
            add(new User(null, "Bob", "email@mail.ru", "password", Role.ROLE_ADMIN));
            add(new User(null, "abba", "email@mail.ru", "password", Role.ROLE_ADMIN));
        }
        //Смысла в этом перекладывании не вижу. Но сказали сделать как Meal
    };


    {
        users.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
            repository.put(user.getId(), user);
            return user;
        }
        //Ничего не делаем если нет такого элемента
        return repository.computeIfPresent(user.getId(), (oldUser, newUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get {}", id);
        return repository.get(id);
    }

    @Override
    public Collection<User> getAll() {
        log.info("getAll");
        //Сортируем игнорируя регистр
        return repository.values().stream().
                sorted(Comparator.comparing(user -> user.getName().toUpperCase())).collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        User user = null;
        try {
            user = repository.entrySet().stream().
                    filter(item -> item.getValue().getEmail().equals(email)).findFirst().get().getValue();
        } catch (NoSuchElementException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }
}
