package com.cefet.vocealuga.entities;

import com.cefet.vocealuga.entities.enums.StatusTransferencia;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;
import java.util.Objects;

@Entity
public class TransferenciaVeiculos {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "estoque_origem_id")
    private Estoque estoqueOrigem;
    @ManyToOne
    @JoinColumn(name = "estoque_destino_id")
    private Estoque estoqueDestino;
    //Gerente
    @Column(columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    private Instant data;
    @ManyToMany
    @JoinTable(
            name = "transferenciaVeiculos_Veiculo",
            joinColumns = @JoinColumn(name = "transferencia_id"),
            inverseJoinColumns = @JoinColumn(name = "veiculo_id")
    )
    private List<Veiculo> veiculos;
    private StatusTransferencia status;



    public void notificarTransferencia() {
        // lógica de notificação
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        TransferenciaVeiculos that = (TransferenciaVeiculos) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
