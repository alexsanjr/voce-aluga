import { Icon } from "@iconify/react/dist/iconify.js";

import "./HomeEscolha.min.css";

const HomeEscolha: React.FC = () => {
    return (
        <>
            <section className="escolhas">
                <div className="title">
                    <strong>
                        Por que escolher a <span>você aluga</span>
                    </strong>
                    <p>Nós nos esforçamos ao máximo para tornar sua experiência de aluguel excepcional.</p>
                </div>

                <ul className="cards">
                    <li>
                        <div className="icon">
                            <i className="reserva">
                                <Icon icon="material-symbols:bolt-rounded" />
                            </i>
                        </div>

                        <strong>Reserva Instantânea</strong>
                        <p>
                            Reserve seu veículo em segundos com nossa plataforma fácil de usar. Receba confirmação
                            instantânea e tranquilidade.
                        </p>
                    </li>
                    <li>
                        <div className="icon">
                            <i className="protecao">
                                <Icon icon="fa-solid:shield-alt" />
                            </i>
                        </div>

                        <strong>Cobertura Completa</strong>
                        <p>
                            Seguro abrangente incluso em todas as locações. Dirija com confiança, sabendo que está
                            totalmente protegido.
                        </p>
                    </li>
                    <li>
                        <div className="icon">
                            <i className="atendimento">
                                <Icon icon="fa6-solid:headset" />
                            </i>
                        </div>

                        <strong>Suporte 25/7</strong>
                        <p>
                            Nossa equipe de atendimento ao cliente está disponível 24 horas por dia para ajudar você com
                            qualquer dúvida ou problema.
                        </p>
                    </li>
                </ul>
            </section>
        </>
    );
};

export default HomeEscolha;
