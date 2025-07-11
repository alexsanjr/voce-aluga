import { Routes, Route } from "react-router-dom";
import Home from "../pages/Home/Home";
import Login from "../pages/Login/Login";
import Reserva from "../pages/Reserva/Reserva";
import Register from "../pages/Register/Register";
import Pagamento from "../pages/Pagamento/Pagamento";
import HomeAluguel from "../pages/Aluguel/Aluguel";

export default function Router() {
  return (
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/login" element={<Login />} />
        <Route path="/register" element={<Register />} />
        {/*<Route path="/veiculos" element={< />} />*/}
        <Route path="/reserva" element={<Reserva />} />
        {/*<Route path="/minha-reserva" element={<Login />} />*/}

        <Route path="/pagamento" element={<Pagamento />} />

        <Route path="/aluguel" element={<HomeAluguel />} />

      </Routes>
  );
}
