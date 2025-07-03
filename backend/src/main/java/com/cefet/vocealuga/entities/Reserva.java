package com.cefet.vocealuga.entities;

import com.cefet.vocealuga.entities.enums.StatusReserva;
import com.cefet.vocealuga.entities.enums.TipoReserva;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.Objects;

@Entity
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //Usuario
    //Motorista
    private TipoReserva categoria;
    private StatusReserva status;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant dataReserva;
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant dataVencimento;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "filial_id")
    private Filial localRetirada;

    public Reserva(Long id, TipoReserva categoria, StatusReserva status, Instant dataReserva, Instant dataVencimento, Filial localRetirada) {
        this.id = id;
        this.categoria = categoria;
        this.status = status;
        this.dataReserva = dataReserva;
        this.dataVencimento = dataVencimento;
        this.localRetirada = localRetirada;
    }

    public Reserva() {
    }

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

    public Filial getLocalRetirada() {
        return localRetirada;
    }

    public void setLocalRetirada(Filial localRetirada) {
        this.localRetirada = localRetirada;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Reserva reserva = (Reserva) o;
        return Objects.equals(id, reserva.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
