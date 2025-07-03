package com.cefet.vocealuga.dtos;

import com.cefet.vocealuga.entities.enums.StatusReserva;
import com.cefet.vocealuga.entities.enums.TipoReserva;

import java.time.Instant;

public class ReservaDTO {

    private Long id;
    //Usuario
    //Motorista
    private TipoReserva categoria;
    private StatusReserva status;
    private Instant dataReserva;
    private Instant dataVencimento;
    private Long localRetiradaId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoReserva getCategoria() {
        return categoria;
    }

    public void setCategoria(TipoReserva categoria) {
        this.categoria = categoria;
    }

    public StatusReserva getStatus() {
        return status;
    }

    public void setStatus(StatusReserva status) {
        this.status = status;
    }

    public Instant getDataReserva() {
        return dataReserva;
    }

    public void setDataReserva(Instant dataReserva) {
        this.dataReserva = dataReserva;
    }

    public Instant getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Instant dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Long getLocalRetiradaId() {
        return localRetiradaId;
    }

    public void setLocalRetiradaId(Long localRetiradaId) {
        this.localRetiradaId = localRetiradaId;
    }
}
