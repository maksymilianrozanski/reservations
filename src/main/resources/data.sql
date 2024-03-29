SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE user;
TRUNCATE TABLE role;
TRUNCATE TABLE user_roles;
TRUNCATE TABLE reservations;
SET FOREIGN_KEY_CHECKS = 1;

INSERT INTO user (user_id, email, first_name, last_name, password, username)
VALUES (1, 'example@example.com', 'John', 'Brown',
        '$2a$10$8bSvjjfn9smNuViinaGy0erNIwyJysYTvjC2XkBcikTXSU5hzPN6u', 'admin');
/* password: 'pass' */

INSERT INTO role (id, role_name)
VALUES (1, 'ADMIN');
INSERT INTO role (id, role_name)
VALUES (2, 'USER');

INSERT INTO user_roles (user_id, roles_id)
VALUES (1, 1);

INSERT INTO user_roles (user_id, roles_id)
VALUES (1, 2);

INSERT INTO user (user_id, email, first_name, last_name, password, username)
VALUES (2, 'example2@example.com', 'John', 'Black',
        '$2a$10$8bSvjjfn9smNuViinaGy0erNIwyJysYTvjC2XkBcikTXSU5hzPN6u', 'user1');

INSERT INTO user_roles (user_id, roles_id)
VALUES (2, 2);

INSERT INTO user(user_id, email, first_name, last_name, password, username)
VALUES (3, 'example3@example.com', 'Marry', 'Smith',
        '$2a$10$8bSvjjfn9smNuViinaGy0erNIwyJysYTvjC2XkBcikTXSU5hzPN6u', 'user2');
INSERT INTO user_roles (user_id, roles_id)
values (3, 2);

INSERT INTO reservations(reservation_id, description, start, end, title, user_id)
VALUES (10, 'Two hours long reservation.', '2019-09-02 14:58:48', '2019-09-02 16:58:48', 'Doctor Jones: control visit', 2);

INSERT INTO reservations(reservation_id, description, start, end, title, user_id)
VALUES (11, 'Dental treatment, one hour long reservation.', '2019-09-02 17:00:00', '2019-09-02 18:00:00',
        'Doctor Smith: dental treatment', null);

