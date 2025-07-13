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
            value: "1",
        },
        {
            label: "Azul",
            value: "2",
        },
        {
            label: "Cinza",
            value: "3",
        },
        {
            label: "Preto",
            value: "4",
        },
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
                                    />
                                    <InputText
                                        label="Grupo"
                                        icon="lets-icons:group-fill"
                                        value={veiculo.grupo}
                                        onChange={(e) => pegarVeiculo("grupo", e.target.value)}
                                    />
                                    <InputText
                                        label="Ano"
                                        icon="solar:calendar-bold"
                                        onChange={(e) => pegarVeiculo("ano", e.target.value)}
                                    />
                                    <InputSelect
                                        label="Cor"
                                        icon="ion:color-filter-sharp"
                                        options={cores}
                                        onChange={(e) => pegarVeiculo("cor", e.target.value)}
                                    />
                                </div>

                                <div className="column">
                                    <InputText
                                        label="Valor diário"
                                        icon="material-symbols:attach-money-rounded"
                                        value={veiculo.valor_diaria}
                                        onChange={(e) => pegarVeiculo("valor_diaria", e.target.value)}
                                    />
                                    <InputText
                                        label="KM"
                                        icon="icon-park-solid:map-distance"
                                        value={veiculo.km}
                                        onChange={(e) => pegarVeiculo("km", e.target.value)}
                                    />
                                    <InputText
                                        label="Status do veículo"
                                        icon="pajamas:status-alert"
                                        value={veiculo.status_veiculo}
                                        onChange={(e) => pegarVeiculo("status_veiculo", e.target.value)}
                                    />
                                    <InputText
                                        label="Estoque ID"
                                        icon="mingcute:stock-fill"
                                        value={veiculo.estoque_id}
                                        onChange={(e) => pegarVeiculo("estoque_id", e.target.value)}
                                    />
                                    <InputText
                                        label="Placa"
                                        icon="solar:plate-bold"
                                        value={veiculo.placa}
                                        onChange={(e) => pegarVeiculo("placa", e.target.value)}
                                    />
                                </div>
                            </div>

                            <button>Salvar</button>
                        </form>
                    </section>
                </section>
            </DashboardDefaults>
        </>
    );
};

export default NovoVeiculo;
