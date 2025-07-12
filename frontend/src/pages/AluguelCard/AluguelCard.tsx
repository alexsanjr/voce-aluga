import React from "react";
import type { Veiculo } from "../../types/veiculo";
import car from "../../assets/background-banner.png";
import "./AluguelCard.css";

interface AluguelCardProps {
  veiculo: Veiculo;
  onReservar?: (veiculo: Veiculo) => void;
}


const AluguelCard: React.FC<AluguelCardProps> = ({ veiculo, onReservar }) => {
  return (
    <div className="aluguel-card aluguel-card-elegante">
      <div className="aluguel-card-imgbox">
        <img
          src={veiculo.imagemUrl || car}
          alt={veiculo.modelo}
          className="aluguel-card-img"
        />
      </div>
      <div className="aluguel-card-info">
        <h3 className="aluguel-card-title">{veiculo.marca} {veiculo.modelo}</h3>
        <div className="aluguel-card-details">
          <span>Ano <b>{veiculo.ano}</b></span>
          <span>Cor <b>{veiculo.cor}</b></span>
          <span>Placa <b>{veiculo.placa}</b></span>
        </div>
      </div>
      <button className="aluguel-card-btn" onClick={() => onReservar?.(veiculo)}>
        Reservar agora
      </button>
    </div>
  );
};

export default AluguelCard;
