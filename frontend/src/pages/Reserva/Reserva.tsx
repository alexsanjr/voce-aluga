

import React, { useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import type { Veiculo } from "../../types/veiculo";
import car from "../../assets/background-banner.png";
// import { criarReserva } from "../../services/reservaService";
import "./Reserva.css";

const categorias = [
  { label: "Imediata", value: "IMEDIATA" },
  { label: "Antecipada", value: "ANTECIPADA" },
];

const Reserva: React.FC = () => {
  const location = useLocation();
  const navigate = useNavigate();
  const veiculo: Veiculo | undefined = location.state?.veiculo;

  const [categoria, setCategoria] = useState("IMEDIATA");
  const todayStr = new Date().toISOString().slice(0, 10);
  const [dataReserva, setDataReserva] = useState(todayStr);
  const [dataVencimento, setDataVencimento] = useState("");
  const [status] = useState("AGENDADO");
  const [erro, setErro] = useState("");

  if (!veiculo) {
    return <div>Veículo não selecionado. Volte e escolha um veículo.</div>;
  }

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    setErro("");
    if (!dataReserva || !dataVencimento) {
      setErro("Preencha todas as datas.");
      return;
    }
    if (dataVencimento < todayStr) {
      setErro("A data de vencimento não pode ser anterior a hoje.");
      return;
    }
    if (dataVencimento < dataReserva) {
      setErro("A data de vencimento não pode ser anterior à data da reserva.");
      return;
    }

    const reservaPayload = {
      categoria,
      status,
      dataReserva,
      dataVencimento,
      localRetiradaId: veiculo.estoqueId,
      veiculoId: veiculo.id,
      veiculo: {
        ...veiculo
      }
    };
    navigate("/pagamento", { state: { reservaPayload } });
  };

  // Atualiza dataReserva para hoje se categoria mudar para IMEDIATA
  React.useEffect(() => {
    if (categoria === "IMEDIATA") {
      setDataReserva(todayStr);
    }
  }, [categoria, todayStr]);

  return (
    <div className="reserva-container">
      <h2>Reserva de Veículo</h2>
      <div className="reserva-content">
        <div className="reserva-veiculo-box">
          <div className="reserva-veiculo-imgbox">
            <img
              src={veiculo.imagemUrl || car}
              alt={veiculo.modelo}
            />
          </div>
          <div className="reserva-veiculo-title">
            {veiculo.marca} {veiculo.modelo}
          </div>
          <div className="reserva-veiculo-info">
            Placa: <b>{veiculo.placa}</b><br />
            Ano: <b>{veiculo.ano}</b> <br />
            Cor: <b>{veiculo.cor}</b> <br />
            Filial: <b>{veiculo.estoqueId}</b>
          </div>
        </div>
        <form className="reserva-form" onSubmit={handleSubmit}>
          <label>
            Categoria:
            <select value={categoria} onChange={e => setCategoria(e.target.value)}>
              {categorias.map(opt => (
                <option key={opt.value} value={opt.value}>{opt.label}</option>
              ))}
            </select>
          </label>
          <label>
            Data da Reserva:
            <input
              type="date"
              value={dataReserva}
              min={todayStr}
              onChange={e => setDataReserva(e.target.value)}
              disabled={categoria === "IMEDIATA"}
            />
          </label>
          <label>
            Data de Vencimento:
            <input
              type="date"
              value={dataVencimento}
              min={dataReserva || todayStr}
              onChange={e => setDataVencimento(e.target.value)}
            />
          </label>
          <label>
            Local de Retirada (ID):
            <input type="text" value={veiculo.estoqueId} disabled />
          </label>
          <label>
            Status:
            <input type="text" value={status} disabled />
          </label>
          {erro && <div className="erro">{erro}</div>}
          <button type="submit">Confirmar Reserva</button>
        </form>
      </div>
    </div>
  );
};

export default Reserva;
