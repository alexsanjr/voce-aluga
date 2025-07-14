import DashboardDefaults from "../../../components/dashboard-defaults/dashboard-defaults";
import { InputSelect, InputText } from "../../../components/inputs";
import useHookVeiculo from "./hook/useHookVeiculo";

import "./novo_veiculo.min.css";

const NovoVeiculo: React.FC = () => {
    const { enviar_form, pegarVeiculo, veiculo } = useHookVeiculo();

    const marcas = [
        {
            label: "Volkswagen",
            value: "Volkswagen",
        },
        {
            label: "Fiat",
            value: "Fiat",
        },
        {
            label: "Chevrolet",
            value: "Chevrolet",
        },
    ];

    const cores = [
        {
            label: "Branco",
            value: "Branco",
        },
        {
            label: "Azul",
            value: "Azul",
        },
        {
            label: "Cinza",
            value: "Cinza",
        },
        {
            label: "Preto",
            value: "Preto",
        },
    ];

    const grupos = [
        {
            label: "A",
            value: "A",
        },
        {
            label: "B",
            value: "B",
        },
        {
            label: "C",
            value: "C",
        }
    ]

    const estoques = [
        { label: "Estoque 1 - Rio de Janeiro", value: "1" },
        { label: "Estoque 2 - São Paulo", value: "2" },
        { label: "Estoque 3 - Salvador", value: "3" }
    ];

    // Opções de status do veículo
    const statusVeiculoOptions = [
        { label: "RESERVADO", value: "RESERVADO" },
        { label: "DISPONIVEL", value: "DISPONIVEL" },
        { label: "EM_USO", value: "EM_USO" },
        { label: "MANUTENCAO", value: "MANUTENCAO" },
    ];

    return (
        <>
            <DashboardDefaults title="Adicionar novo veículo">
                <section className="criar-veiculo">
                    <section className="form-criar-veiculo">
                        <form onSubmit={enviar_form}>
                            <div className="grid">
                                <div className="column">
                                    <InputSelect
                                        icon="mingcute:car-fill"
                                        label="Marca"
                                        options={marcas}
                                        value={veiculo.marca}
                                        onChange={(e) => pegarVeiculo("marca", e.target.value)}
                                    />
                                    <InputText
                                        label="Modelo"
                                        icon="mdi:car-info"
                                        value={veiculo.modelo}
                                        onChange={(e) => pegarVeiculo("modelo", e.target.value)}
                                        placeholder="Ex: Gol, Onix, Uno..."
                                    />
                                    <InputSelect
                                        label="Grupo"
                                        icon="lets-icons:group-fill"
                                        options={grupos}
                                        value={veiculo.grupo || ""}
                                        onChange={(e) => pegarVeiculo("grupo", e.target.value)}
                                    />
                                    <InputSelect
                                        label="Ano"
                                        icon="solar:calendar-bold"
                                        options={Array.from({ length: new Date().getFullYear() - 1999 }, (_, i) => {
                                            const year = 2000 + i;
                                            return { label: year.toString(), value: year.toString() };
                                        }).reverse()}
                                        value={veiculo.ano}
                                        onChange={(e) => pegarVeiculo("ano", e.target.value)}
                                    />
                                    <InputSelect
                                        label="Cor"
                                        icon="ion:color-filter-sharp"
                                        options={cores}
                                        value={veiculo.cor}
                                        onChange={(e) => pegarVeiculo("cor", e.target.value)}
                                    />
                                </div>

                                <div className="column">
                                    <InputText
                                        label="Valor diário"
                                        icon="material-symbols:attach-money-rounded"
                                        value={
                                            veiculo.valorDiaria !== undefined && veiculo.valorDiaria !== null && veiculo.valorDiaria !== ""
                                                ? Number(veiculo.valorDiaria).toLocaleString("pt-BR", { style: "currency", currency: "BRL" })
                                                : ""
                                        }
                                        onChange={(e) => {
                                            // Aceita apenas números
                                            let value = e.target.value.replace(/\D/g, "").slice(0, 10);
                                            // Converte para centavos
                                            let numeric = value ? (parseInt(value, 10) / 100).toFixed(2) : "";
                                            pegarVeiculo("valorDiaria", numeric);
                                        }}
                                        placeholder="R$ 0,00"
                                        inputMode="numeric"
                                        maxLength={10}
                                    />
                                    <InputText
                                        label="KM"
                                        icon="icon-park-solid:map-distance"
                                        value={veiculo.quilometragem}
                                        onChange={(e) => {
                                            // Aceita apenas números
                                            let value = e.target.value.replace(/\D/g, "").slice(0, 10);
                                            pegarVeiculo("quilometragem", value);
                                        }}
                                        placeholder="Ex: 50000"
                                        inputMode="numeric"
                                        maxLength={10}
                                    />
                                    <InputSelect
                                        label="Status do veículo"
                                        icon="pajamas:status-alert"
                                        options={statusVeiculoOptions}
                                        value={veiculo.statusVeiculo}
                                        onChange={(e) => pegarVeiculo("status_veiculo", e.target.value)}
                                    />
                                    <InputSelect
                                        label="Estoque ID"
                                        icon="mingcute:stock-fill"
                                        options={estoques}
                                        value={veiculo.estoqueId || ""}
                                        onChange={(e) => pegarVeiculo("estoqueId", e.target.value)}
                                    />
                                    <InputText
                                        label="Placa"
                                        icon="solar:plate-bold"
                                        value={veiculo.placa}
                                        onChange={(e) => {
                                            // Permitir apenas letras e números, converter para maiúsculo
                                            let value = e.target.value.toUpperCase().replace(/[^A-Z0-9]/g, "");
                                            // Limitar a 7 caracteres
                                            value = value.slice(0, 7);
                                            pegarVeiculo("placa", value);
                                        }}
                                        placeholder="ABC1D23 ou ABC1234"
                                        maxLength={7}
                                        pattern="[A-Z]{3}[0-9][A-Z0-9][0-9]{2}"
                                        title="Placa no formato ABC1D23 ou ABC1234"
                                    />
                                </div>
                            </div>

                            <button>Criar</button>
                        </form>
                    </section>
                </section>
            </DashboardDefaults>
        </>
    );
};

export default NovoVeiculo;
