package com.cefet.vocealuga.entities;

import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class Funcionario extends Usuario {
    private String cargo;
    private String filial;

    public Funcionario() {
    }

    public Funcionario(Long id, String nome, String documento, LocalDate dataDeNascimento, String email, String password, String telefone, String cargo, String filial) {
        super(id, nome, documento, dataDeNascimento, email, password, telefone);
        this.cargo = cargo;
        this.filial = filial;
    }

    public Funcionario(Long id, String email, String password, String cargo, String filial) {
        super(id, email, password);
        this.cargo = cargo;
        this.filial = filial;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getFilial() {
        return filial;
    }

    public void setFilial(String filial) {
        this.filial = filial;
    }
}
