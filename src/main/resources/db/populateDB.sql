DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, calories,description,user_id) VALUES
   ( to_date('2015/05/30/10','YYYY/MM/DD/HH'),500,'Тортик',100000),
   ( to_date('2015/05/30/11','YYYY/MM/DD/HH'),1000,'',100000),
   ( to_date('2015/05/30/20','YYYY/MM/DD/HH'),500,'',100000),
   ( to_date('2015/05/31/10','YYYY/MM/DD/HH'),1000,'',100000),
   ( to_date('2015/05/31/11','YYYY/MM/DD/HH'),500,'',100000),
   ( to_date('2015/05/31/12','YYYY/MM/DD/HH'),510,'',100000)




