CREATE DATABASE students;

USE students;



CREATE TABLE student_record (
student_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, 
student_name VARCHAR(100), 
student_username VARCHAR(100), 
student_password VARCHAR(20),
student_gender VARCHAR(1), 
programming_skill VARCHAR(200),
contact_number VARCHAR(10),
student_email VARCHAR(50), 
student_college VARCHAR(100));


SELECT * FROM student_record;