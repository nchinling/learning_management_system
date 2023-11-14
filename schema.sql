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

create table class(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    account_id VARCHAR(20) NOT NULL, 
    class_name VARCHAR(10) NOT NULL
);

create table student_result(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    student_account_id  VARCHAR(5) NOT NULL, 
    quiz_id VARCHAR(20) NOT NULL,
    marks NUMERIC NOT NULL
);

create table quiz_data(
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    quiz_id VARCHAR(20) NOT NULL, 
    attempts NUMERIC NOT NULL DEFAULT 0
);

INSERT INTO class (account_id, class_name)
VALUES ('ccf05864', 'Class A');

INSERT INTO class (account_id, class_name)
VALUES ('ccf05864', 'Class B');

INSERT INTO class (account_id, class_name)
VALUES ('ccf05864', 'Class C');

UPDATE class
SET class_name = "Class E"
WHERE id=5;

insert into student_account
values(0, 'student0', 'student0@email.com', 'student0@eduquest', 'Class A');

insert into student_account
values(1, 'student1', 'student1@email.com', 'student1@eduquest', 'Class A');