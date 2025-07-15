import React, { useEffect, useState } from "react";
import { getMe } from "../../services/meService";
import api from "../../services/api";
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
    localRetiradaNome?: string; // Novo campo
}

const MinhasReservas: React.FC = () => {
    const [reservas, setReservas] = useState<Reserva[]>([]);
    const [loading, setLoading] = useState(true);
    const [erro, setErro] = useState("");

    useEffect(() => {
        async function fetchReservas() {
            setLoading(true);
            setErro("");
            try {
                const me = await getMe();
                const reservaIds: number[] = me.reservas || [];
                const reservasDetalhes = await Promise.all(
                    reservaIds.map(async (id) => {
                        const res = await api.get(`/reservas/${id}`);
                        const reserva = res.data;
                        // Busca o nome da filial/local de retirada
                        let localRetiradaNome = "";
                        try {
                            const filialRes = await api.get(`/filiais/${reserva.localRetiradaId}`);
                            localRetiradaNome = filialRes.data.nome || `ID ${reserva.localRetiradaId}`;
                        } catch {
                            localRetiradaNome = `ID ${reserva.localRetiradaId}`;
                        }
                        return { id, ...reserva, localRetiradaNome };
                    }),
                );
                setReservas(reservasDetalhes);
            } catch (err) {
                console.error("Erro detalhado:", err);
                setErro("Erro ao buscar reservas do usuário.");
            } finally {
                setLoading(false);
            }
        }
        fetchReservas();
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
                                <div className="minhas-reserva-info">
                                    <div className="minhas-reserva-title">Reserva #{r.id}</div>
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
                                        Local de Retirada: <b>{r.localRetiradaNome}</b>
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
