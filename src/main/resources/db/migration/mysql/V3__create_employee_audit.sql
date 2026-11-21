CREATE TABLE IF NOT EXISTS employee_audit(
    id BIGINT PRIMARY KEY auto_increment,
    nome VARCHAR(150),
    old_name VARCHAR(150),
    salary DECIMAL(10, 2),
    old_salary DECIMAL(10, 2),
    birthday TIMESTAMP,
    old_birthday TIMESTAMP,
    operation CHAR(1),
    created_at DATETIME DEFAULT CURRENT_TIMESTAMP
);