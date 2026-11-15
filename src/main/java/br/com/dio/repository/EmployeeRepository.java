package br.com.dio.repository;

import br.com.dio.entity.EmployeeEntity;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    void insert(final EmployeeEntity entity);
    void update(final EmployeeEntity entity);
    void delete(final long id);
    Optional<EmployeeEntity> findById(final long id);
    List<EmployeeEntity> findAll();
}
