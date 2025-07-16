import api from "./api";

const API_URL = "/agendar-manutencao";

export interface EstacaoServico {
    id: number;
    nome: string;
}

export interface ManutencaoPayload {
    veiculoId: number;
    estacaoDeServicoId: number;
    motivoManutencao: string;
}

export const getManutencoes = async () => {
    const response = await api.get(API_URL);
    return response.data;
};

export const getManutencaoById = async (id: number) => {
    const response = await api.get(`${API_URL}/${id}`);
    return response.data;
};

export const agendarManutencao = async (payload: ManutencaoPayload) => {
    const response = await api.post(`${API_URL}`, payload);
    return response.data;
};

export const finalizarManutencao = async (id: number) => {
    const response = await api.patch(`${API_URL}/${id}/finalizar`);
    return response.data;
};

export const getEstacaoDeServico = async () => {
    const response = await api.get("/estacoes-servico");
    return response.data;
};
