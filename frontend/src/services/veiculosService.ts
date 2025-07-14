import api from "./api";

const API_URL = "/veiculos";

// Busca todos os veículos (com ou sem filtro de marca)
export const getAllVeiculosDisponivel = async (marca = "") => {
    const response = await getAllVeiculos();

    console.log("Response data:", response);

    // Suporta resposta paginada (content) ou lista direta
    const veiculos = Array.isArray(response) ? response : (response.content || []);
    // Filtra apenas veículos com status DISPONIVEL
    return veiculos.filter((v: any) => v.statusVeiculo === "DISPONIVEL");
};

// Busca todos os veículos (sem filtro)
export const getAllVeiculos = async () => {
    const response = await api.get(API_URL);
    return response.data;
};

// Cadastra um novo veículo
export const createVeiculo = async (veiculo: any) => {
    const response = await api.post(API_URL, veiculo);
    return response.data;
};
