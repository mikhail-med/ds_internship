insert into internship.role (name) values ('USER');
insert into internship.role (name) values ('ADMIN');
-- root pass: adminpass
insert into internship.user (name, email, phone_number, username, telegram_id, info, date_of_birth, city, password)
VALUES ('root', '', '', 'root', '', 'root admin user', '2012-12-12 00:00:00.00', '', '$2a$10$HC7cST.xW3tM0e14vsd0eOaUM0KL7TRG2olThZXR15HCcusblmexO');

insert into internship.user_roles(user_id, role_id) values (1, 2);