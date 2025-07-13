import { Routes, Route } from "react-router-dom";
import PrivateRoute from "./PrivateRoute";
import Home from "../pages/Home/Home";
import Login from "../pages/Login/Login";
import Reserva from "../pages/Reserva/Reserva";
import Register from "../pages/Register/Register";
import Pagamento from "../pages/Pagamento/Pagamento";
import Aluguel from "../pages/Aluguel/Aluguel";
import MinhasReservas from "../pages/MinhasReservas/MinhasReservas";

export default function Router() {
  return (
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        {/*<Route path="/veiculos" element={< />} />*/}
        <Route path="/reserva" element={<PrivateRoute><Reserva /></PrivateRoute>} />
        <Route path="/minhas-reservas" element={<MinhasReservas />} />

        <Route path="/pagamento" element={<PrivateRoute><Pagamento /></PrivateRoute>} />

        <Route path="/aluguel" element={<PrivateRoute><Aluguel /></PrivateRoute>} />

      </Routes>
  );
}
