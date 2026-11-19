-- Employee Management System - Database Schema
-- Run this script in MySQL before starting the application

CREATE DATABASE IF NOT EXISTS employee_management;

USE employee_management;

CREATE TABLE IF NOT EXISTS employees (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)   NOT NULL,
    email       VARCHAR(100)   NOT NULL UNIQUE,
    phone       VARCHAR(15)    NOT NULL,
    department  VARCHAR(50)    NOT NULL,
    designation VARCHAR(50)    NOT NULL,
    salary      DECIMAL(10, 2) NOT NULL,
    hire_date   DATE           NOT NULL
);

-- Sample data (optional)
INSERT INTO employees (name, email, phone, department, designation, salary, hire_date) VALUES
('Alice Johnson',  'alice@example.com',  '9876543210', 'Engineering', 'Software Engineer',  75000.00, '2022-01-15'),
('Bob Smith',      'bob@example.com',    '9876543211', 'HR',          'HR Manager',         60000.00, '2021-06-01'),
('Carol Williams', 'carol@example.com',  '9876543212', 'Finance',     'Financial Analyst',  65000.00, '2023-03-20');
