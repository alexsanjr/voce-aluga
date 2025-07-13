import DashboardDefaults from "../../../components/dashboard-defaults/dashboard-defaults";
import { InputDate, InputSelect, InputText } from "../../../components/inputs";

const NovoVeiculo: React.FC = () => {
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
                        <form>
                            <InputSelect icon="mingcute:car-fill" label="Marca" options={marcas} />
                            <InputText label="Modelo" icon="mdi:car-info" />
                            <InputText label="Grupo" icon="lets-icons:group-fill" />
                            <InputDate label="Ano" icon="solar:calendar-bold" />
                            <InputSelect label="Cor" icon="ion:color-filter-sharp" options={cores} />
                            <InputText label="Valor diário" icon="material-symbols:attach-money-rounded" />
                            <InputText label="KM" icon="icon-park-solid:map-distance" />
                            <InputText label="Status do veículo" icon="pajamas:status-alert" />
                            <InputText label="Estoque ID" icon="mingcute:stock-fill" />
                            <InputText label="Placa" icon="solar:plate-bold" />

                            <button>Submit</button>
                        </form>
                    </section>
                </section>
            </DashboardDefaults>
        </>
    );
};

export default NovoVeiculo;
