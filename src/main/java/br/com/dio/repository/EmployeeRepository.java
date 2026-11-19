package br.com.dio.repository;

import br.com.dio.entity.EmployeeEntity;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository {

    /**
     * Persiste o employee e preenche o id gerado na própria entity.
     * <p>
     * Datas/horas (birthDate): a implementação deve converter entre
     * {@link java.time.OffsetDateTime} e o tipo nativo do banco, sempre
     * em UTC, para que o valor lido seja igual ao gravado.
     */

    void insert(final EmployeeEntity entity);
    void update(final EmployeeEntity entity);
    void delete(final long id);
    Optional<EmployeeEntity> findById(final long id);

    /**
     * Lista employees paginados, ordenados por id.
     *
     * @param page número da página, começando em 0
     * @param size quantidade por página (entre 1 e 100)
     */
    List<EmployeeEntity> findAll(int page, int size);
}
