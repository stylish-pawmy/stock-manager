INSERT INTO application_user (id, username, password) VALUES (0, 'admin', 'password');
SELECT nextval('application_user_seq');

INSERT INTO role (id, name) VALUES (0, 'ROLE_ADMIN');
SELECT nextval('role_seq');

INSERT INTO application_user_roles(application_user_id, roles_id) VALUES (0, 0);