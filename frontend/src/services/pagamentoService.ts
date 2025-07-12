
import api from "./api";

const API_URL = "/pagamento";

export const criarPagamento = async (payload: any) => {
  const response = await api.post(API_URL, payload);
  console.log("Pagamento criado:", response.data);
  return response.data;
};
