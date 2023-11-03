create table accounts(
    account_id VARCHAR(10) NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL
);


create table student_account(
    account_id VARCHAR(10) NOT NULL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(50) NOT NULL,
    class VARCHAR(10) NOT NULL
);

insert into student_account
values(0, 'student0', 'student0@email.com', 'student0@eduquest', 'Class A');

insert into student_account
values(1, 'student1', 'student1@email.com', 'student1@eduquest', 'Class A');