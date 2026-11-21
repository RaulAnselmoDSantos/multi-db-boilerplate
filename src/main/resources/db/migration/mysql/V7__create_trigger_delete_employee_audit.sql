CREATE TRIGGER tgr_employee_audit_delete BEFORE DELETE ON employee
    FOR EACH ROW
BEGIN
    INSERT INTO employee_audit (employee_id, nome, salary, birthday, operation)
    VALUES (OLD.id, OLD.nome, OLD.salary, OLD.birth_date, 'D');
END;