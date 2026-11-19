CREATE DATABASE IF NOT EXISTS hrm_db;
USE hrm_db;

CREATE TABLE IF NOT EXISTS employees (
    id          INT AUTO_INCREMENT PRIMARY KEY,
    name        VARCHAR(100)  NOT NULL,
    email       VARCHAR(100)  NOT NULL UNIQUE,
    phone       VARCHAR(20)   NOT NULL,
    department  VARCHAR(50)   NOT NULL,
    role        VARCHAR(50)   NOT NULL,
    salary      DECIMAL(10,2) NOT NULL DEFAULT 0.00,
    hire_date   DATE          NOT NULL,
    status      ENUM('Active','Disabled') NOT NULL DEFAULT 'Active'
);

INSERT INTO employees (name, email, phone, department, role, salary, hire_date, status) VALUES
('BusinessOwner',  'owner@company.com',   '9000000001', 'Management',  'Business Owner',  120000.00, '2020-01-01', 'Active'),
('Test User',      'test@company.com',    '9000000002', 'Management',  'Business Owner',   90000.00, '2021-03-15', 'Active'),
('Test Manager',   'testmgr@company.com', '9000000003', 'Sales',       'Sales Manager',    75000.00, '2021-06-01', 'Active'),
('salesmanager11', 'smgr11@company.com',  '9000000004', 'Sales',       'Sales Manager',    72000.00, '2022-01-10', 'Active'),
('Saleman1',       'sale1@company.com',   '9000000005', 'Sales',       'Sales Person',     50000.00, '2022-04-20', 'Active'),
('saleman1',       'sale1b@company.com',  '9000000006', 'Sales',       'Sales Person',     48000.00, '2022-05-01', 'Active'),
('cashier444',     'cash444@company.com', '9000000007', 'Finance',     'Cashier',          45000.00, '2023-02-15', 'Active');
