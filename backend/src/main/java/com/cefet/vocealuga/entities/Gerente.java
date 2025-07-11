package com.cefet.vocealuga.entities;

import java.time.LocalDate;

public class Gerente extends Funcionario {

    public Gerente() {
    }

    public Gerente(Long id, String nome, String documento, LocalDate dataDeNascimento, String email, String password, String telefone, String cargo, String filial) {
        super(id, nome, documento, dataDeNascimento, email, password, telefone, cargo, filial);
    }

    public Gerente(Long id, String email, String password, String cargo, String filial) {
        super(id, email, password, cargo, filial);
    }
}
