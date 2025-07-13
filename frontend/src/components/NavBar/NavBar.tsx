import "./NavBar.min.css";
import logoImg from "../../assets/logo_footer_s.png";

import { useState } from "react";

export default function NavBar() {
    const [isLogged, setIsLogged] = useState(() => !!localStorage.getItem("token"));

    function handleLogout(e: React.MouseEvent) {
        e.preventDefault();
        localStorage.removeItem("token");
        setIsLogged(false);
        window.location.href = "/login";
    }

    // Atualiza o estado se o token mudar em outra aba
    window.addEventListener("storage", () => {
        setIsLogged(!!localStorage.getItem("token"));
    });

    return (
        <div className="container-navbar">
            <div className="limit">
                <a href="/" className="logo">
                    <figure>
                        <img src={logoImg} alt="Logo" />
                    </figure>
                </a>

                <nav className="navegacao">
                    <ul className="menus">
                        <li className="HomePage">
                            <a href="/">HOME</a>
                        </li>
                        <li className="ReservarAgora">
                            <a href="/aluguel">RESERVAR AGORA</a>
                        </li>
                        <li className="MinhasReservas">
                            <a href="/minhas-reservas">MINHAS RESERVAS</a>
                        </li>
                        {isLogged ? (
                            <li className="Logout">
                                <a href="#" onClick={handleLogout}>SAIR</a>
                            </li>
                        ) : (
                            <li className="Login">
                                <a href="/login">ENTRAR</a>
                            </li>
                        )}
                    </ul>
                </nav>
            </div>
        </div>
    );
}
