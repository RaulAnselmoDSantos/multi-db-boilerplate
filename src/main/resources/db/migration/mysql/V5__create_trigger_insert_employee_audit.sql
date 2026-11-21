CREATE TRIGGER tgr_employee_audit_insert AFTER INSERT ON employee
    FOR EACH ROW
BEGIN
    INSERT INTO employee_audit (employee_id, nome, salary, birthday, operation)
    VALUES (NEW.id, NEW.nome, NEW.salary, NEW.birth_date, 'I');
END;