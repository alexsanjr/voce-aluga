import api from "./api";

export const register = async (email: string, password: string, tipo: string) => {
  const response = await api.post(
    "/register",
     { email, password, tipo},
    { headers: { Authorization: "" } }
);
  return response.data;
};
