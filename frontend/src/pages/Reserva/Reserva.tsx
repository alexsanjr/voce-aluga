import { useEffect, useState } from "react";
import "./Reserva.css";

interface Car {
  id: number;
  modelo: string;
  categoria: string;
  ano: number;
  combustivel: string;
  cambio: string;
  arCondicionado: boolean;
  imagem: string;
  preco: number;
}

export default function Reserva() {
  const [carros, setCarros] = useState<Car[]>([]);

  useEffect(() => {
    setCarros([
      {
        id: 1,
        modelo: "Toyota Corolla Altis",
        categoria: "Sedã Médio",
        ano: 2023,
        combustivel: "Flex",
        cambio: "Automático",
        arCondicionado: true,
        imagem: "/assets/carros/corolla.png",
        preco: 199.9,
      },
      {
        id: 2,
        modelo: "Jeep Compass Limited",
        categoria: "SUV",
        ano: 2024,
        combustivel: "Diesel",
        cambio: "Automático",
        arCondicionado: true,
        imagem: "/assets/carros/compass.png",
        preco: 259.9,
      },
      {
        id: 3,
        modelo: "Volkswagen T-Cross",
        categoria: "SUV Compacto",
        ano: 2023,
        combustivel: "Flex",
        cambio: "Automático",
        arCondicionado: true,
        imagem: "/assets/carros/tcross.png",
        preco: 189.9,
      },
      {
        id: 4,
        modelo: "Fiat Mobi Like 1.0",
        categoria: "Econômico",
        ano: 2022,
        combustivel: "Flex",
        cambio: "Manual",
        arCondicionado: true,
        imagem: "/assets/carros/mobi.png",
        preco: 79.9,
      },
      {
        id: 5,
        modelo: "Renault Kwid Zen",
        categoria: "Econômico",
        ano: 2023,
        combustivel: "Flex",
        cambio: "Manual",
        arCondicionado: true,
        imagem: "/assets/carros/kwid.png",
        preco: 84.9,
      },
      {
        id: 6,
        modelo: "Chevrolet Tracker Premier",
        categoria: "SUV",
        ano: 2024,
        combustivel: "Flex",
        cambio: "Automático",
        arCondicionado: true,
        imagem: "/assets/carros/tracker.png",
        preco: 229.9,
      },
    ]);
  }, []);

  return (
    <div className="reserva-container">
      <div className="reserva-container-veicles">
        <section className="veiculos-disponiveis">
          <h2>Escolha seu próximo veículo</h2>
          <div className="veiculos-grid">
            {carros.map((carro) => (
              <div className="card-veiculo" key={carro.id}>
                <img
                  src={carro.imagem}
                  alt={carro.modelo}
                  className="carro-img"
                />
                <h3>{carro.modelo}</h3>
                <p className="categoria">
                  {carro.categoria} • {carro.ano}
                </p>
                <p className="detalhes">
                  {carro.combustivel} • {carro.cambio} •{" "}
                  {carro.arCondicionado
                    ? "Ar-condicionado"
                    : "Sem ar-cond."}
                </p>
                <p className="preco">
                  R$ {carro.preco.toFixed(2)} <span>/ dia</span>
                </p>
                <button className="btn-reservar">Reservar</button>
              </div>
            ))}
          </div>
        </section>
      </div>
    </div>
  );
}
