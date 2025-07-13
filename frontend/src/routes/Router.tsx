import { Routes, Route } from "react-router-dom";
import PrivateRoute from "./PrivateRoute";
import Home from "../pages/Home/Home";
import Login from "../pages/Login/Login";
import Reserva from "../pages/Reserva/Reserva";
import Register from "../pages/Register/Register";
import Pagamento from "../pages/Pagamento/Pagamento";
import Aluguel from "../pages/Aluguel/Aluguel";
import MinhasReservas from "../pages/MinhasReservas/MinhasReservas";

import { lazy } from "react";

const Adm = lazy(() => import("../pages/adm/adm"));
const NovoVeiculo = lazy(() => import("../pages/adm/novo_veiculo/novo_veiculo"));

export default function Router() {
    return (
        <Routes>
            <Route path="/" element={<Home />} />
            <Route path="/login" element={<Login />} />
            <Route path="/register" element={<Register />} />
            {/*<Route path="/veiculos" element={< />} />*/}
            <Route
                path="/reserva"
                element={
                    <PrivateRoute>
                        <Reserva />
                    </PrivateRoute>
                }
            />

            <Route
                path="/minhas-reservas"
                element={
                    <PrivateRoute>
                        <MinhasReservas />
                    </PrivateRoute>
                }
            />

            <Route
                path="/pagamento"
                element={
                    <PrivateRoute>
                        <Pagamento />
                    </PrivateRoute>
                }
            />

            <Route
                path="/aluguel"
                element={
                    <PrivateRoute>
                        <Aluguel />
                    </PrivateRoute>
                }
            />

            <Route
                path="/adm"
                element={
                    // <PrivateRoute>
                    <Adm />
                    // </PrivateRoute>
                }
            />

            <Route
                path="/novoveiculo"
                element={
                    // <PrivateRoute>
                    <NovoVeiculo />
                    // </PrivateRoute>
                }
            />
        </Routes>
    );
}
