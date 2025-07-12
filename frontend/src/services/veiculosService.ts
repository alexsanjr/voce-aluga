
import api from "./api";

const API_URL = "/veiculos";

export const getVeiculos = async (marca = "") => {
  const response = await api.get(
    API_URL, 
    { params: { marca }, headers: { Authorization: "" }},
  );
  console.log("Veículos recebidos:", response.data);
  return response.data;
};
