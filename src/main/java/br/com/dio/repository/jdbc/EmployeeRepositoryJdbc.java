package br.com.dio.repository.jdbc;

import br.com.dio.database.ConnectionFactory;
import br.com.dio.entity.EmployeeEntity;
import br.com.dio.repository.EmployeeRepository;


import java.sql.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeRepositoryJdbc implements EmployeeRepository {

    private final ConnectionFactory factory;
    private static final int MAX_PAGE_SIZE = 100;

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

        var sql = "SELECT id, nome, email, salary, birth_date " +
                "FROM employee WHERE id = ?";

        Optional<EmployeeEntity> employees = Optional.empty();

        try (
                var connection = factory.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ){

            ps.setLong(1, ((long) id));

            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    return Optional.of(map(rs));
                }
            }

        }catch (SQLException ex) {
            throw new RuntimeException("Error with FindById in Employee", ex);
        }

        return Optional.empty();
    }

    @Override
    public List<EmployeeEntity> findAll(int page, int size) {
        if(page < 0){
            throw new IllegalArgumentException("Page number cannot be less than zero");
        }
        if(size < MAX_PAGE_SIZE){
            throw new IllegalArgumentException("Size cannot be more than 100");
        }

        var sql = "SELECT nome, email, salary, birth_date " +
                "FROM employee ORDER BY id LIMIT ? OFFSET ?";

        List<EmployeeEntity> employees = new ArrayList<>();

        try (
                var connection = factory.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        ){

            ps.setInt(1, size);
            ps.setLong(2, ((long) page * size));

            try(ResultSet rs = ps.executeQuery()) {
                while(rs.next()){
                    employees.add(map(rs));
                }
            }

        }catch (SQLException ex) {
            throw new RuntimeException("Error with FindAll in Employee", ex);
        }

        return employees;
    }

    private EmployeeEntity map(ResultSet rs) throws SQLException {
        EmployeeEntity entity = new EmployeeEntity();
        entity.setId(rs.getLong("id"));
        entity.setNome(rs.getString("nome"));
        entity.setEmail(rs.getString("email"));
        entity.setSalary(rs.getBigDecimal("salary"));
        entity.setBirthDate(rs.getObject("birth_date", OffsetDateTime.class));
        return entity;
    }
}
