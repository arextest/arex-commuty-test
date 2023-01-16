use community;
DROP TABLE IF EXISTS user;
CREATE TABLE user
(
    id INTEGER,
    username VARCHAR(200) NOT NULL,
    password VARCHAR(200) NOT NULL,
    enabled tinyint default 1,
    locked tinyint default 0,
    roles VARCHAR(200) NULL,
    PRIMARY KEY (`id`)
);
INSERT INTO user (id, username, password) VALUES (1, 'root', '$2a$10$VXoCHAJUG1Qkq04bSmVeJuvg2G8w2CxZMnKK.uKMMuJlUcGErx9hi');
INSERT INTO user (id, username, password) VALUES (2, 'admin', '$2a$10$VXoCHAJUG1Qkq04bSmVeJuvg2G8w2CxZMnKK.uKMMuJlUcGErx9hi');
INSERT INTO user (id, username, password) VALUES (3, 'lulu', '$2a$10$VXoCHAJUG1Qkq04bSmVeJuvg2G8w2CxZMnKK.uKMMuJlUcGErx9hi');


DROP TABLE IF EXISTS role;
CREATE TABLE role
(
    id INTEGER,
    name VARCHAR(200) NOT NULL,
    nameZH VARCHAR(200) NOT NULL,
    PRIMARY KEY (`id`)
);
INSERT INTO role (id, name, nameZH) VALUES (1, 'ROLE_dba', '数据库管理员');
INSERT INTO role (id, name, nameZH) VALUES (2, 'ROLE_admin', '系统管理员');
INSERT INTO role (id, name, nameZH) VALUES (3, 'ROLE_user', '用户');

DROP TABLE IF EXISTS user_role;
CREATE TABLE user_role
(
    id INTEGER,
    uid INTEGER,
    rid INTEGER,
    PRIMARY KEY (`id`)
);
INSERT INTO user_role (id, uid, rid) VALUES (1, 1, 1);
INSERT INTO user_role (id, uid, rid) VALUES (2, 1, 2);
INSERT INTO user_role (id, uid, rid) VALUES (3, 2, 2);
INSERT INTO user_role (id, uid, rid) VALUES (4, 3, 3);
