package br.com.dio.repository.jdbc;

import br.com.dio.database.ConnectionFactory;
import br.com.dio.entity.EmployeeEntity;
import br.com.dio.repository.EmployeeRepository;


import java.util.List;
import java.util.Optional;

public class EmployeeRepositoryJdbc implements EmployeeRepository {

    private final ConnectionFactory factory;

    public EmployeeRepositoryJdbc(ConnectionFactory factory){
        this.factory = factory;
    }

    @Override
    public void insert(EmployeeEntity entity) {

    }

    @Override
    public void update(EmployeeEntity entity) {

    }

    @Override
    public void delete(long id) {

    }

    @Override
    public Optional<EmployeeEntity> findById(long id) {
        return null;
    }

    @Override
    public List<EmployeeEntity> findAll() {
        return List.of();
    }

}
