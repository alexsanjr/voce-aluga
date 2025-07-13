import { Navigate } from "react-router-dom";
import type { ReactNode } from "react";


import { parseJwt } from "../utils/jwt";

interface PrivateRouteProps {
  children: ReactNode;
  role?: string;
}

export default function PrivateRoute({ children, role }: PrivateRouteProps) {
  const token = localStorage.getItem("token");
  if (!token) {
    return <Navigate to="/login" replace />;
  }
  if (role) {
    const payload = parseJwt(token);
    if (!payload || payload.role !== role) {
      return <Navigate to="/aluguel" replace />;
    }
  }
  return <>{children}</>;
}
