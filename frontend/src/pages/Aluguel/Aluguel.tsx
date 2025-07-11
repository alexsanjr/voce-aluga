import { InputDate, InputSelect } from "../../components/inputs";

import "./aluguel.min.css";
const HomeAluguel: React.FC = () => {
    const options = [
        {
            label: "Campo Grande - RJ",
            value: "Campo grande - RH",
        },
        {
            label: "Nova Iguaçu - RJ",
            value: "Nova Iguaçu - RH",
        },
    ];
    return (
        <>
            <section className="container-aluguel">
                <div className="container">
                    <strong className="titulo">Encontre o seu carro perfeito</strong>

                    <div className="inputs">
                        <InputSelect icon={"mdi:location"} label={"Local de retirada"} options={options} />
                        <InputDate icon={"mdi:location"} label={"Data de retirada"} />
                    </div>
                </div>
            </section>
        </>
    );
};

export default HomeAluguel;
