
import { useEffect, useState } from "react";


import { InputDate, InputSelect } from "../../components/inputs";
import { getVeiculos } from "../../services/veiculosService";
import type { Veiculo } from "../../types/veiculo";
import AluguelCard from "../AluguelCard/AluguelCard";
import NavBar from "../../components/NavBar/NavBar";
import Footer from "../../components/Footer/Footer";
import "./Aluguel.css";

const options = [
  { label: "Campo Grande - RJ", value: "Campo Grande - RJ" },
  { label: "Nova Iguaçu - RJ", value: "Nova Iguaçu - RJ" },
];

const Aluguel: React.FC = () => {
  const [local, setLocal] = useState("");
  const [dataRetirada, setDataRetirada] = useState("");
  const [veiculos, setVeiculos] = useState<Veiculo[]>([]);
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    buscarVeiculos();
    // eslint-disable-next-line
  }, []);

  async function buscarVeiculos() {
    setLoading(true);
    try {
      // Aqui pode-se passar o filtro de marca ou local se backend suportar
      const data = await getVeiculos(local);
      setVeiculos(data.content || []);
    } finally {
      setLoading(false);
    }
  }


  function handleLocalChange(e: React.ChangeEvent<HTMLSelectElement>) {
    setLocal(e.target.value);
  }

  function handleDataChange(e: React.ChangeEvent<HTMLInputElement>) {
    setDataRetirada(e.target.value);
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
              options={options}
              value={local}
              onChange={handleLocalChange}
            />
            <InputDate
              icon={"mdi:calendar"}
              label={"Data de retirada"}
              value={dataRetirada}
              onChange={handleDataChange}
            />
          </div>
          <div style={{ marginTop: "2rem", display: "flex", flexWrap: "wrap", gap: "1.5rem", justifyContent: "center" }}>
            {loading ? (
              <span>Carregando veículos...</span>
            ) : veiculos.length === 0 ? (
              <span>Nenhum veículo encontrado.</span>
            ) : (
              veiculos.map((v) => (
                <AluguelCard key={v.id} veiculo={v} />
              ))
            )}
          </div>
        </div>
      </section>
      <Footer />
    </>
  );
};

export default Aluguel;
