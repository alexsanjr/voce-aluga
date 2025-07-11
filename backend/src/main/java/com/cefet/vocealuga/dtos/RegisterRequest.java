package com.cefet.vocealuga.dtos;

import com.cefet.vocealuga.dtos.enums.TipoRegister;

public class RegisterRequest {
    private String email;
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
}
