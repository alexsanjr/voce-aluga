import "./NavBar.min.css";
import logoImg from "../../assets/logo_footer_s.png";

export default function NavBar() {
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
                            <a href="#">RESERVAR AGORA</a>
                        </li>
                        <li className="MinhasReservas">
                            <a href="#">MINHAS RESERVAS</a>
                        </li>
                        <li className="Login">
                            <a href="/login">ENTRAR</a>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    );
}
