delete from users;

insert into users(id, email, name, phone, surname, password, role, birth_date, gender)
VALUES (1, 'test1@gmail.com', 'testname1', 'testphone1', 'testsurname1', '$2a$08$5n96D5WscwK1/FYC3Ab5Ee32dlGdVzkF94UUuqGeBYEva8AF.Aikm', 'USER', '2001-07-30', 'Male'),
       (2, 'test2@gmail.com', 'testname2', 'testphone2', 'testsurname2', '$2a$08$5n96D5WscwK1/FYC3Ab5Ee32dlGdVzkF94UUuqGeBYEva8AF.Aikm', 'USER', '2001-07-31', 'Female'),
       (3, 'admin1@gmail.com', 'adminname', 'adminphone', 'adminsurname1', '$2a$08$5n96D5WscwK1/FYC3Ab5Ee32dlGdVzkF94UUuqGeBYEva8AF.Aikm', 'ADMIN', '2001-07-31', 'Male')
#        all passwords are testpassword1