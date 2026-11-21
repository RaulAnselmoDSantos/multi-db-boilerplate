CREATE TRIGGER tgr_employee_audit_update BEFORE UPDATE ON employee
    FOR EACH ROW
BEGIN
    INSERT INTO employee_audit (employee_id, nome, old_name, salary, old_salary, birthday, old_birthday, operation)
    VALUES (NEW.id, NEW.nome, OLD.nome, NEW.salary, OLD.salary, NEW.birth_date, OLD.birth_date,'U');
END;