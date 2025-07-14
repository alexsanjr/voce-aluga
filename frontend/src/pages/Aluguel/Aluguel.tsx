import { useEffect, useState } from "react";
import { InputDate, InputSelect } from "../../components/inputs";
import { getAllVeiculosDisponivel } from "../../services/veiculosService";
import type { Veiculo } from "../../types/veiculo";
import AluguelCard from "../AluguelCard/AluguelCard";
import NavBar from "../../components/NavBar/NavBar";
import Footer from "../../components/Footer/Footer";
import { marcas as marcasOptions, locais } from "../../utils/veiculoOptions";
import "./Aluguel.css";

const marcas = [
  { label: "Todas", value: "" },
  ...marcasOptions
];

const Aluguel: React.FC = () => {
    const [marca, setMarca] = useState("");
    const [local, setLocal] = useState("");
    const [veiculos, setVeiculos] = useState<Veiculo[]>([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        buscarVeiculos();
        // eslint-disable-next-line
    }, [marca, local]);

  async function buscarVeiculos() {
    setLoading(true);
    try {
      let veiculosDisponiveis = await getAllVeiculosDisponivel(marca);
      if (local) {
        veiculosDisponiveis = veiculosDisponiveis.filter((v: Veiculo) => String(v.filialId) === local);
      }
      setVeiculos(veiculosDisponiveis);
    } finally {
      setLoading(false);
    }
  }

    function handleMarcaChange(e: React.ChangeEvent<HTMLSelectElement>) {
        setMarca(e.target.value);
    }
    function handleLocalChange(e: React.ChangeEvent<HTMLSelectElement>) {
        setLocal(e.target.value);
    }

    return (
        <>
            <NavBar />
            <section className="container-aluguel">
                <div className="container">
                    <strong className="titulo">Encontre o seu carro perfeito</strong>
                    <div className="inputs">
                        <InputSelect
                            icon={"mdi:location"}
                            label={"Local de retirada"}
                            options={locais}
                            value={local}
                            onChange={handleLocalChange}
                        />
                        <InputSelect
                            icon={"mdi:car"}
                            label={"Marca"}
                            options={marcas}
                            value={marca}
                            onChange={handleMarcaChange}
                        />
                    </div>
                    <div className="aluguel-cards-list">
                        {loading ? (
                            <span>Carregando veículos...</span>
                        ) : veiculos.length === 0 ? (
                            <span>Nenhum veículo encontrado.</span>
                        ) : (
                            veiculos.map((v) => <AluguelCard key={v.id} veiculo={v} />)
                        )}
                    </div>
                </div>
            </section>
            <Footer />
        </>
    );
};

export default Aluguel;
