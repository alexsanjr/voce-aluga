import { useEffect, useState } from "react";
import { getVeiculos } from "../../../services/veiculosService";
import type { Veiculo } from "../../../types/veiculo";
import AluguelCard from "../../AluguelCard/AluguelCard";
import "./HomeAluguel.css";

const HomeAluguel: React.FC = () => {
  const [veiculos, setVeiculos] = useState<Veiculo[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    buscarVeiculos();
    // eslint-disable-next-line
  }, []);

  async function buscarVeiculos() {
    setLoading(true);
    try {
      const data = await getVeiculos();
      setVeiculos((data.content || []).slice(0, 2));
    } finally {
      setLoading(false);
    }
  }

  return (
    <section className="container-aluguel home-aluguel">
      <div className="container">
        <strong className="titulo">Destaques da nossa frota</strong>
        <div className="aluguel-cards-list">
          {loading ? (
            <span>Carregando veículos...</span>
          ) : veiculos.length === 0 ? (
            <span>Nenhum veículo em destaque.</span>
          ) : (
            veiculos.map((v) => <AluguelCard key={v.id} veiculo={v} />)
          )}
        </div>
      </div>
    </section>
  );
};

export default HomeAluguel;
