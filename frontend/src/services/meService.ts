import api from "./api";

export const getMe = async () => {
  const response = await api.get("/auth/me");
  return response.data;
};
