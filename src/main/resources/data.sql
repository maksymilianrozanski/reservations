TRUNCATE TABLE user;
TRUNCATE TABLE role;
TRUNCATE TABLE user_roles;

INSERT INTO user (id, account_non_expired, account_non_locked, credentials_non_expired, email,
                  enabled, first_name, last_name, pass_word, user_name, version)
VALUES (1, 1, 1, 1, 'example@example.com', 1, 'firstname', 'lastname',
        '$2a$10$8bSvjjfn9smNuViinaGy0erNIwyJysYTvjC2XkBcikTXSU5hzPN6u', 'admin', 1);
# pass_word: 'pass'

INSERT INTO role (id, role_name)
VALUES (1, 'ADMIN');
INSERT INTO role (id, role_name)
VALUES (2, 'USER');

INSERT INTO user_roles (user_id, roles_id)
VALUES (1, 1);

INSERT INTO user_roles (user_id, roles_id)
VALUES (1, 2);
