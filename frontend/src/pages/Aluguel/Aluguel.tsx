import { useEffect, useState } from "react";

import { InputSelect } from "../../components/inputs";
import { getVeiculos } from "../../services/veiculosService";
import type { Veiculo } from "../../types/veiculo";
import AluguelCard from "../AluguelCard/AluguelCard";
import NavBar from "../../components/NavBar/NavBar";
import Footer from "../../components/Footer/Footer";
import "./Aluguel.css";

const marcas = [
    { label: "Todas", value: "" },
    { label: "Fiat", value: "Fiat" },
    { label: "Volkswagen", value: "Volkswagen" },
    { label: "Chevrolet", value: "Chevrolet" },
    { label: "Ford", value: "Ford" },
    { label: "Toyota", value: "Toyota" },
    { label: "Hyundai", value: "Hyundai" },
    { label: "Renault", value: "Renault" },
    { label: "Honda", value: "Honda" },
    { label: "Nissan", value: "Nissan" },
    { label: "Peugeot", value: "Peugeot" },
    { label: "Citroën", value: "Citroën" },
    { label: "Kia", value: "Kia" },
    { label: "Outros", value: "Outros" },
];

const locais = [
    { label: "Todos", value: "" },
    { label: "Campo Grande - RJ", value: "Campo Grande - RJ" },
    { label: "Nova Iguaçu - RJ", value: "Nova Iguaçu - RJ" },
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
            // Aqui você pode adaptar para passar ambos filtros se o backend aceitar
            const data = await getVeiculos(marca); // Adapte para getVeiculos(marca, local) se necessário
            let filtrados = data.content || [];
            if (local) {
                filtrados = filtrados.filter((v: Veiculo) =>
                    (v.filial || "").toLowerCase().includes(local.toLowerCase())
                );
            }
            setVeiculos(filtrados);
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
