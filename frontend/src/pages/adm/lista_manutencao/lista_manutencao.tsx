import { Icon } from "@iconify/react/dist/iconify.js";
import DashboardDefaults from "../../../components/dashboard-defaults/dashboard-defaults";
import { useState, useEffect } from "react";
import type { ReactNode } from "react";
import { InputSelect } from "../../../components/inputs";
import { locais } from "../../../utils/veiculoOptions";
import "./lista_manutencao.min.css";
import {
    agendarManutencao,
    getManutencoes,
    finalizarManutencao,
    getEstacaoDeServico,
    type ManutencaoPayload
} from "../../../services/manutencaoService";
import useHookAdmList from "./hook/useHookAdmList";
import type { ApiError } from "../../../types/api-error";

interface Manutencao extends ManutencaoPayload {
    id: number;
    status?: "AGENDADO" | "EM_ANDAMENTO" | "FINALIZADO" | "CANCELADO";
}

interface EstacaoServico {
    id: number;
    nome: string;
}

const statusLabels: Record<string, string> = {
    AGENDADO: "Agendada",
    EM_ANDAMENTO: "Em andamento",
    FINALIZADO: "Finalizada",
    CANCELADO: "Cancelada"
};

const ListaManutencao: React.FC = () => {
    const { Lista_veiculos, atualizarLista } = useHookAdmList();
    const [manutencoes, setManutencoes] = useState<Manutencao[]>([]);
    const [estacoesServico, setEstacoesServico] = useState<EstacaoServico[]>([]);
    const [loading, setLoading] = useState(false);
    const [modalAberto, setModalAberto] = useState(false);
    const [motivoManutencao, setMotivoManutencao] = useState("");
    const [veiculoSelecionado, setVeiculoSelecionado] = useState<number | null>(null);
    const [estacaoSelecionada, setEstacaoSelecionada] = useState("");
    const [filialSelecionada, setFilialSelecionada] = useState("");

    useEffect(() => {
        carregarManutencoes();
        carregarEstacoes();
    }, []);

    const carregarManutencoes = async () => {
        setLoading(true);
        try {
            const data = await getManutencoes();
            setManutencoes(Array.isArray(data) ? data : []);
        } catch (err) {
            handleError(err);
        } finally {
            setLoading(false);
        }
    };

    const carregarEstacoes = async () => {
        try {
            const data = await getEstacaoDeServico();
            setEstacoesServico(Array.isArray(data) ? data : []);
        } catch (err) {
            handleError(err);
        }
    };

    const handleError = (error: any) => {
        console.error("Erro:", error);
        if (error.response?.data) {
            const apiError = error.response.data as ApiError;
            alert(apiError.message || "Ocorreu um erro. Tente novamente.");
        } else {
            alert("Ocorreu um erro inesperado. Tente novamente.");
        }
    };

    const handleNovaManutencao = () => {
        setModalAberto(true);
    };

    const handleSubmitManutencao = async () => {
        if (!veiculoSelecionado || !estacaoSelecionada || !motivoManutencao) {
            alert("Preencha todos os campos");
            return;
        }

        const payload: ManutencaoPayload = {
            veiculoId: veiculoSelecionado,
            estacaoDeServicoId: Number(estacaoSelecionada),
            motivoManutencao
        };

        setLoading(true);
        try {
            await agendarManutencao(payload);
            setModalAberto(false);

            if (atualizarLista) {
                await atualizarLista();
            }
            await carregarManutencoes();

            // Limpar campos
            setMotivoManutencao("");
            setEstacaoSelecionada("");
            setVeiculoSelecionado(null);
        } catch (err) {
            handleError(err);
        } finally {
            setLoading(false);
        }
    };

    const handleFinalizarManutencao = async (manutencaoId: number) => {
        if (window.confirm("Deseja finalizar esta manutenção?")) {
            try {
                await finalizarManutencao(manutencaoId);
                await carregarManutencoes();
                if (atualizarLista) {
                    await atualizarLista();
                }
            } catch (err) {
                handleError(err);
            }
        }
    };

    const getAcoes = (manutencao: Manutencao) => {
        const acoes: ReactNode[] = [];

        if (manutencao.status === "AGENDADO") {
            acoes.push(
                <button
                    key="iniciar"
                    className="start-maintenance"
                    title="Iniciar manutenção"
                    onClick={() => handleFinalizarManutencao(manutencao.id)}
                >
                    <i>
                        <Icon icon="tabler:player-play" />
                    </i>
                </button>
            );
        }

        if (manutencao.status === "EM_ANDAMENTO") {
            acoes.push(
                <button
                    key="finalizar"
                    className="finish-maintenance"
                    title="Finalizar manutenção"
                    onClick={() => handleFinalizarManutencao(manutencao.id)}
                >
                    <i>
                        <Icon icon="tabler:check" />
                    </i>
                </button>
            );
        }

        return acoes;
    };

    return (
        <DashboardDefaults title="Gerenciamento de manutenção">
            <section className="container-table-frota">
                <div className="title">
                    <button onClick={handleNovaManutencao}>
                        <i>
                            <Icon icon="tabler:tool" />
                        </i>
                        <p>Nova Manutenção</p>
                    </button>

                    <InputSelect
                        options={locais}
                        icon="icon-park-solid:city"
                        value={filialSelecionada}
                        onChange={(e) => setFilialSelecionada(e.target.value)}
                    />
                </div>

                <div className="table">
                    <div className="section-title">
                        <h3>Manutenções</h3>
                    </div>
                    <div className="arredondar">
                        {loading ? (
                            <div>Carregando...</div>
                        ) : (
                            <table>
                                <thead>
                                    <tr>
                                        <th>Veículo</th>
                                        <th>Placa</th>
                                        <th>Estação de Serviço</th>
                                        <th>Motivo</th>
                                        <th>Status</th>
                                        <th>Ações</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    {manutencoes.map((manutencao) => {
                                        const veiculo = Lista_veiculos.find(v => v.id === manutencao.veiculoId);
                                        const estacao = estacoesServico.find(e => e.id === manutencao.estacaoDeServicoId);

                                        return (
                                            <tr key={manutencao.id}>
                                                <td>{veiculo ? `${veiculo.marca} ${veiculo.modelo}` : 'N/A'}</td>
                                                <td>{veiculo?.placa || 'N/A'}</td>
                                                <td>{estacao?.nome || 'N/A'}</td>
                                                <td>{manutencao.motivoManutencao}</td>
                                                <td className="status">
                                                    <span className={`status-${manutencao.status}`}>
                                                        {statusLabels[manutencao.status || ""] || manutencao.status}
                                                    </span>
                                                </td>
                                                <td className="edit">
                                                    <div>{getAcoes(manutencao)}</div>
                                                </td>
                                            </tr>
                                        );
                                    })}
                                </tbody>
                            </table>
                        )}
                    </div>
                </div>
            </section>

            {/* Modal de Agendamento */}
            {modalAberto && (
                <div className="modal-overlay">
                    <div className="modal-content">
                        <h3>Agendar Manutenção</h3>
                        <form onSubmit={(e) => { e.preventDefault(); handleSubmitManutencao(); }}>
                            <div className="form-group">
                                <label>Veículo</label>
                                <select
                                    value={veiculoSelecionado || ""}
                                    onChange={(e) => setVeiculoSelecionado(Number(e.target.value))}
                                    required
                                >
                                    <option value="">Selecione um veículo</option>
                                    {Lista_veiculos
                                        .filter(v => v.statusVeiculo === "DISPONIVEL")
                                        .map(veiculo => (
                                            <option key={veiculo.id} value={veiculo.id}>
                                                {veiculo.marca} {veiculo.modelo} - {veiculo.placa}
                                            </option>
                                    ))}
                                </select>
                            </div>

                            <div className="form-group">
                                <label>Estação de Serviço</label>
                                <select
                                    value={estacaoSelecionada}
                                    onChange={(e) => setEstacaoSelecionada(e.target.value)}
                                    required
                                >
                                    <option value="">Selecione uma estação</option>
                                    {estacoesServico.map(estacao => (
                                        <option key={estacao.id} value={estacao.id}>
                                            {estacao.nome}
                                        </option>
                                    ))}
                                </select>
                            </div>

                            <div className="form-group">
                                <label>Motivo da Manutenção</label>
                                <textarea
                                    value={motivoManutencao}
                                    onChange={(e) => setMotivoManutencao(e.target.value)}
                                    required
                                    rows={3}
                                />
                            </div>

                            <div className="modal-actions">
                                <button type="button" onClick={() => setModalAberto(false)}>
                                    Cancelar
                                </button>
                                <button type="submit" disabled={loading}>
                                    {loading ? 'Agendando...' : 'Agendar Manutenção'}
                                </button>
                            </div>
                        </form>
                    </div>
                </div>
            )}
        </DashboardDefaults>
    );
};

export default ListaManutencao;
