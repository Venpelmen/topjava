DELETE
FROM user_roles;
DELETE
FROM users;
DELETE
FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, calories, description, user_id)
VALUES (TO_TIMESTAMP('2015-05-30 9:30', 'YYYY-MM-DD HH:MI'), 500, 'Тортик', 100000),
       (TO_TIMESTAMP('2015-05-30 10:30', 'YYYY-MM-DD HH:MI'), 1000, 'Тортик', 100000),
       (TO_TIMESTAMP('2015-05-30 11:30', 'YYYY-MM-DD HH:MI'), 500, 'Тортик', 100000),
       (TO_TIMESTAMP('2015-05-31 9:30', 'YYYY-MM-DD HH:MI'), 1000, 'Тортик', 100000),
       (TO_TIMESTAMP('2015-05-31 10:30', 'YYYY-MM-DD HH:MI'), 500, 'Тортик', 100000),
       (TO_TIMESTAMP('2015-05-31 11:30', 'YYYY-MM-DD HH:MI'), 510, 'Тортик', 100000);







