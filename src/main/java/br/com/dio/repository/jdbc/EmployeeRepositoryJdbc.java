package br.com.dio.repository.jdbc;

import br.com.dio.database.ConnectionFactory;
import br.com.dio.entity.EmployeeEntity;
import br.com.dio.repository.EmployeeRepository;


import java.sql.*;
import java.util.List;
import java.util.Optional;

public class EmployeeRepositoryJdbc implements EmployeeRepository {

    private final ConnectionFactory factory;

    public EmployeeRepositoryJdbc(ConnectionFactory factory){
        this.factory = factory;
    }

    @Override
    public void insert(EmployeeEntity entity) {
        var sql = "INSERT INTO employee (nome, email, salary, birth_date) VALUES (?,?,?,?)";
        try (
                var connection = factory.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){

            ps.setString(1, entity.getNome());
            ps.setString(2, entity.getEmail());
            ps.setBigDecimal(3, entity.getSalary());
            ps.setObject(4, entity.getBirthDate());

            ps.executeUpdate();

            try(ResultSet generatedKeys = ps.getGeneratedKeys()){
                if(generatedKeys.next()){
                    entity.setId(generatedKeys.getInt(1));
                }
            }

        }catch (SQLException ex) {
            throw new RuntimeException("Error with insert in Employee", ex);
        }
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
