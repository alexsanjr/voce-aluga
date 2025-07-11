package com.cefet.vocealuga.entities;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Administrador  extends Funcionario{

    public Administrador() {
    }

    public Administrador(Long id, String nome, String documento, LocalDate dataDeNascimento, String email, String password, String telefone, String cargo, String filial) {
        super(id, nome, documento, dataDeNascimento, email, password, telefone, cargo, filial);
    }

    public Administrador(Long id, String email, String password, String cargo, String filial) {
        super(id, email, password, cargo, filial);
    }

}
