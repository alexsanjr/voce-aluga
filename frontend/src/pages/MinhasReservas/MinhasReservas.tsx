import React, { useEffect, useState } from "react";
import { getMe } from "../../services/meService";
import "./MinhasReservas.css";
import NavBar from "../../components/NavBar/NavBar";
import Footer from "../../components/Footer/Footer";

interface Reserva {
    id: number;
    categoria: string;
    status: string;
    dataReserva: string;
    dataVencimento: string;
    localRetiradaId: number;
    veiculoId: number;
    veiculo?: {
        modelo: string;
        marca: string;
        placa: string;
        ano: number;
        cor: string;
        imagemUrl?: string;
    };
}

const MinhasReservas: React.FC = () => {
    const [reservas, setReservas] = useState<Reserva[]>([]);
    const [loading, setLoading] = useState(true);
    const [erro, setErro] = useState("");

    useEffect(() => {
        async function fetchMe() {
            setLoading(true);
            setErro("");
            try {
                const me = await getMe();
                setReservas(me.reservas || []);
            } catch (err) {
                setErro("Erro ao buscar reservas do usuário.");
            } finally {
                setLoading(false);
            }
        }
        fetchMe();
    }, []);

    return (
        <>
            <NavBar />
            <div className="minhas-reservas-container">
                <h2>Minhas Reservas</h2>
                {loading ? (
                    <div className="minhas-reservas-loading">Carregando reservas...</div>
                ) : erro ? (
                    <div className="minhas-reservas-erro">{erro}</div>
                ) : reservas.length === 0 ? (
                    <div className="minhas-reservas-vazio">Nenhuma reserva encontrada.</div>
                ) : (
                    <div className="minhas-reservas-list">
                        {reservas.map((r) => (
                            <div className="minhas-reserva-card" key={r.id}>
                                <div className="minhas-reserva-imgbox">
                                    <img
                                        src={r.veiculo?.imagemUrl || "/vite.svg"}
                                        alt={r.veiculo?.modelo || "Veículo"}
                                    />
                                </div>
                                <div className="minhas-reserva-info">
                                    <div className="minhas-reserva-title">
                                        {r.veiculo?.marca} {r.veiculo?.modelo}
                                    </div>
                                    <div className="minhas-reserva-detalhes">
                                        Placa: <b>{r.veiculo?.placa}</b> | Ano: <b>{r.veiculo?.ano}</b> | Cor:{" "}
                                        <b>{r.veiculo?.cor}</b>
                                    </div>
                                    <div className="minhas-reserva-datas">
                                        <span>
                                            Reserva: <b>{r.dataReserva}</b>
                                        </span>{" "}
                                        <span>
                                            Vencimento: <b>{r.dataVencimento}</b>
                                        </span>
                                    </div>
                                    <div className="minhas-reserva-status">
                                        Categoria: <b>{r.categoria}</b> | Status: <b>{r.status}</b>
                                    </div>
                                    <div className="minhas-reserva-local">
                                        Local de Retirada (ID): <b>{r.localRetiradaId}</b>
                                    </div>
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </div>
            <Footer />
        </>
    );
};

export default MinhasReservas;
