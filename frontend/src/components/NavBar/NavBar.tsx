
import "./NavBar.min.css";
import logoImg from "../../assets/logo_footer_s.png";
import { useState, useEffect } from "react";
import { parseJwt } from "../../utils/jwt";


export default function NavBar() {
    const [isLogged, setIsLogged] = useState(() => !!localStorage.getItem("token"));
    const [role, setRole] = useState<string | null>(null);


    function handleLogout(e: React.MouseEvent) {
        e.preventDefault();
        localStorage.removeItem("token");
        setIsLogged(false);
        setRole(null);
        window.location.href = "/login";
    }


    // Atualiza o estado se o token mudar em outra aba
    useEffect(() => {
        function updateAuth() {
            const token = localStorage.getItem("token");
            setIsLogged(!!token);
            if (token) {
                const payload = parseJwt(token);
                setRole(payload?.role || null);
            } else {
                setRole(null);
            }
        }
        updateAuth();
        window.addEventListener("storage", updateAuth);
        return () => window.removeEventListener("storage", updateAuth);
    }, []);


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
                        {isLogged && (role === "ROLE_ADMININISTRADOR" || role === "ROLE_GERENTE") && (
                            <li className="Painel">
                                <a href="/adm">PAINEL</a>
                            </li>
                        )}
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
