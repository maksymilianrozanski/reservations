CREATE  TABLE users (
  userid int(11) NOT NULL AUTO_INCREMENT,
  username VARCHAR(45) NOT NULL,
  password VARCHAR(60) NOT NULL ,
  enabled TINYINT NOT NULL DEFAULT 1 ,
  PRIMARY KEY (userid));

CREATE TABLE user_roles (
  user_role_id int(11) NOT NULL AUTO_INCREMENT PRIMARY KEY,
  userid int(11) NOT NULL ,
  role varchar(45) NOT NULL,
  FOREIGN KEY (userid) REFERENCES users (userid));
  
INSERT INTO users(username,password,enabled)
VALUES ('admin1','pass', true);
INSERT INTO users(username,password,enabled)
VALUES ('user1','pass', true);

INSERT INTO user_roles (userid, role)
VALUES (001, 'ROLE_USER');
INSERT INTO user_roles (userid, role)
VALUES (002, 'ROLE_ADMIN');
INSERT INTO user_roles (userid, role)
VALUES (002, 'ROLE_USER');