import { Icon } from "@iconify/react/dist/iconify.js";
import DashboardDefaults from "../../../components/dashboard-defaults/dashboard-defaults";
import useHookAdmList from "../hook/useHookAdmList";

import "./adm.min.css";
import { useNavigate } from "react-router-dom";

const ListaFrota: React.FC = () => {
    const { Lista_veiculos } = useHookAdmList();

    const navigate = useNavigate();

    return (
        <>
            <DashboardDefaults title="Gerenciamento de frota">
                <section className="container-table-frota">
                    <div className="title">
                        <button onClick={() => navigate(`/novoveiculo`)}>
                            <i>
                                <Icon icon="fluent-emoji-high-contrast:plus" />
                            </i>
                            <p>Adicionar veículo</p>
                        </button>
                    </div>

                    <div className="table">
                        <div className="arredondar">
                            {Lista_veiculos && (
                                <table>
                                    <thead>
                                        <tr>
                                            <th>Marca</th>
                                            <th>Modelo</th>
                                            <th>Grupo</th>
                                            <th>Cor</th>
                                            <th>Ano</th>
                                            <th>KM</th>
                                            <th>Placa</th>
                                            <th style={{ textAlign: "center" }}>Status</th>
                                            <th></th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        {Lista_veiculos.map((item: any) => (
                                            <tr key={item.id}>
                                                <td>{item.marca}</td>
                                                <td>{item.modelo}</td>
                                                <td>{item.grupo}</td>
                                                <td>{item.cor}</td>
                                                <td>{item.ano}</td>
                                                <td>{item.quilometragem}</td>
                                                <td>{item.placa}</td>
                                                <td className="status">
                                                    <span className={item.statusVeiculo}>{item.statusVeiculo}</span>
                                                </td>
                                                <td className="edit">
                                                    <div>
                                                        <button>
                                                            <i>
                                                                <Icon icon="tabler:edit" />
                                                            </i>
                                                        </button>
                                                        <button className="trash">
                                                            <i>
                                                                <Icon icon="iconamoon:trash-fill" />
                                                            </i>
                                                        </button>
                                                    </div>
                                                </td>
                                            </tr>
                                        ))}
                                    </tbody>
                                </table>
                            )}
                        </div>
                    </div>
                </section>
            </DashboardDefaults>
        </>
    );
};

export default ListaFrota;
