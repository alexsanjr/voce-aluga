import { useState, useEffect } from "react";
import { login } from "../../services/loginService";
import { useNavigate } from "react-router-dom";
import NavBar from "../../components/NavBar/NavBar";
import "./Login.css";
import bkgLogin from "../../assets/bkglogin3.jpg";
import { parseJwt } from "../../utils/jwt";

export default function Login() {
    useEffect(() => {
        if (localStorage.getItem("token")) {
            window.location.replace("/aluguel");
        }
    }, []);
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [error, setError] = useState("");
    const navigate = useNavigate();

    async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
        e.preventDefault();
        setError("");
        try {
            const { token } = await login(email, password);
            localStorage.setItem("token", token);
            const payload = parseJwt(token);
            if (payload && payload.role === "ROLE_ADMININISTRADOR") {
                navigate("/adm");
            } else {
                navigate("/aluguel");
            }
        } catch (err: any) {
            setError("Usuário ou senha inválidos");
        }
    }

    return (
        <>
            <NavBar />
            <div className="login-container">
                <div className="login-left" style={{ backgroundImage: `url(${bkgLogin})` }}>
                    <div className="left-overlay">
                        <div className="container-text">
                            <div className="left-text">
                                <h2>Cliente você-aluga: tudo em um só lugar.</h2>
                                <p>Acesse sua conta, veja reservas, edite dados, cancele ou renove contratos.</p>
                            </div>
                            <div className="left-links">
                                <a href="#">Termos de uso</a>
                                <a href="#">Política de privacidade</a>
                                <a href="#">Segurança</a>
                            </div>
                        </div>
                    </div>
                </div>

                <div className="login-right">
                    <div className="login-box">
                        <h2>Acesse sua conta</h2>
                        <form onSubmit={handleSubmit}>
                            <div className="form-group">
                                <label htmlFor="email">E-mail</label>
                                <input
                                    type="email"
                                    id="email"
                                    placeholder="Digite seu e-mail"
                                    required
                                    value={email}
                                    onChange={(e) => setEmail(e.target.value)}
                                />
                            </div>

                            <div className="form-group">
                                <label htmlFor="password">Senha</label>
                                <input
                                    type="password"
                                    id="password"
                                    placeholder="Digite sua senha"
                                    required
                                    value={password}
                                    onChange={(e) => setPassword(e.target.value)}
                                />
                            </div>

                            {error && <div style={{ color: "#c00", marginBottom: 8 }}>{error}</div>}

                            <button type="submit" className="login-button">
                                Entrar
                            </button>
                        </form>

                        <p className="signup-link">
                            Não tem uma conta?{" "}
                            <a href="/register" className="redirect-cadastrar">
                                Cadastre-se
                            </a>
                        </p>
                    </div>
                </div>
            </div>
        </>
    );
}
