package br.com.dio.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Data
public class EmployeeEntity {

    private long id;

    private String nome;

    private String email;

    private BigDecimal salary;

    private OffsetDateTime birthDate;

}
