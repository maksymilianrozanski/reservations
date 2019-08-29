TRUNCATE TABLE user;
TRUNCATE TABLE role;
TRUNCATE TABLE user_roles;

INSERT INTO user (id,  email, first_name, last_name, password, username)
VALUES (1, 'example@example.com',  'firstname', 'lastname',
        '$2a$10$8bSvjjfn9smNuViinaGy0erNIwyJysYTvjC2XkBcikTXSU5hzPN6u', 'admin');
# pass_word: 'pass'

INSERT INTO role (id, role_name)
VALUES (1, 'ADMIN');
INSERT INTO role (id, role_name)
VALUES (2, 'USER');

INSERT INTO user_roles (user_id, roles_id)
VALUES (1, 1);

INSERT INTO user_roles (user_id, roles_id)
VALUES (1, 2);
