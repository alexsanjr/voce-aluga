import api from "./api";

const API_URL = "/reservas";

export const criarReserva = async (payload: any) => {
  const response = await api.post(API_URL, payload);
  return response.data;
};
