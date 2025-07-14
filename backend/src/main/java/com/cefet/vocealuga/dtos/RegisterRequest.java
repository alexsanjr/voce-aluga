package com.cefet.vocealuga.dtos;

import com.cefet.vocealuga.dtos.enums.TipoRegister;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Column;

import java.time.LocalDate;

public class RegisterRequest {
    private String nome;
    @Column(unique = true)
    private String documento;
    private LocalDate dataNascimento;
    @Column(unique = true)
    private String email;
    private String telefone;
    private String password;
    private TipoRegister tipo;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public TipoRegister getTipo() {
        return tipo;
    }

    public void setTipo(TipoRegister tipo) {
        this.tipo = tipo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDocumento() {
        return documento;
    }

    public void setDocumento(String documento) {
        this.documento = documento;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
