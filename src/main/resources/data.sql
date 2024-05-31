-- Initial Roles --
INSERT INTO role (id, name, description) VALUES (0, 'ROLE_ADMIN', 'Administrateur');
SELECT nextval('role_seq');
INSERT INTO role (id, name, description) VALUES (1, 'ROLE_MAGASIN', 'Magasinier');
SELECT nextval('role_seq');
INSERT INTO role (id, name, description) VALUES (2, 'ROLE_INVENTAIRE', 'Inventairier');
SELECT nextval('role_seq');

INSERT INTO application_user (id, username, password, role_id) VALUES (0, 'admin', 'password', 0);
SELECT nextval('application_user_seq');