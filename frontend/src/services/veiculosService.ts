import api from "./api";

const API_URL = "/veiculos";

// Busca todos os veículos (com ou sem filtro de marca)
export const getAllVeiculosDisponivel = async (marca = "") => {
    const response = await getAllVeiculos();

    console.log("Response data:", response);

    // Suporta resposta paginada (content) ou lista direta
    const veiculos = Array.isArray(response) ? response : response.content || [];
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

// Busca um veículo pelo ID
export const getVeiculoById = async (id: string | number) => {
    const response = await api.get(`${API_URL}/${id}`);
    return response.data;
};

// Atualiza um veículo existente
export const updateVeiculo = async (id: string | number, veiculo: any) => {
    const response = await api.put(`${API_URL}/${id}`, veiculo);
    return response.data;
};

// Deleta um veículo pelo ID
export const deleteVeiculo = async (id: string | number) => {
    const response = await api.delete(`${API_URL}/${id}`);
    return response.data;
};

// Upload de imagem para um veículo
export const uploadVeiculoImagem = async (id: string | number, file: File) => {
    const formData = new FormData();
    formData.append('imagem', file);
    
    const response = await api.post(`${API_URL}/${id}/upload-imagem`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    });
    return response.data;
};

// Upload de múltiplas imagens para um veículo
export const uploadVeiculoImagens = async (id: string | number, files: File[]) => {
    const formData = new FormData();
    files.forEach((file, index) => {
        formData.append(`imagens`, file);
    });
    
    const response = await api.post(`${API_URL}/${id}/upload-imagens`, formData, {
        headers: {
            'Content-Type': 'multipart/form-data',
        },
    });
    return response.data;
};

// Deletar uma imagem específica
export const deleteVeiculoImagem = async (id: string | number, imagemUrl: string) => {
    const response = await api.delete(`${API_URL}/${id}/imagem`, {
        data: { imagemUrl }
    });
    return response.data;
};
