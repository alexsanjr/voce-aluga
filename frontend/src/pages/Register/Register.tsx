


import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { register as registerService } from "../../services/registerService";
import NavBar from "../../components/NavBar/NavBar";
import Footer from "../../components/Footer/Footer";
import "./Register.css";
import bkgRegister from "../../assets/bkglogin3.jpg";
import { login } from "../../services/loginService";

export default function Register() {
  useEffect(() => {
    if (localStorage.getItem("token")) {
      window.location.replace("/aluguel");
    }
  }, []);

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);
  const navigate = useNavigate();

  async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();
    setError("");
    setSuccess(false);
    try {
      await registerService(email, password, "CLIENTE");
      setSuccess(true);

      const { token } = await login(email, password);
      localStorage.setItem("token", token);
      navigate("/aluguel");
    } catch (err: any) {
      setError(err?.response?.data?.message || "Erro ao registrar. Tente novamente.");
    }
  }

  return (
    <>
      <NavBar />
      <div className="login-container">
        <div
          className="login-left"
          style={{ backgroundImage: `url(${bkgRegister})` }}
        >
          <div className="left-overlay">
            <div className="container-text">
              <div className="left-text">
                <h2>Cliente você-aluga: tudo em um só lugar.</h2>
                <p>
                  Acesse sua conta, veja reservas, edite dados, cancele ou renove
                  contratos.
                </p>
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
            <h2>Crie sua conta</h2>
            <form onSubmit={handleSubmit}>
              <div className="form-group">
                <label htmlFor="email">E-mail</label>
                <input
                  type="email"
                  id="email"
                  placeholder="Digite seu e-mail"
                  required
                  value={email}
                  onChange={e => setEmail(e.target.value)}
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
                  onChange={e => setPassword(e.target.value)}
                />
              </div>

              {error && <div style={{ color: "#c00", marginBottom: 8 }}>{error}</div>}
              {success && <div style={{ color: "#080", marginBottom: 8 }}>Cadastro realizado! Redirecionando...</div>}

              <button type="submit" className="login-button">
                Registrar-se
              </button>
            </form>

            <p className="signup-link">
              Já possui uma conta? <a href="/login">Entrar</a>
            </p>
          </div>
        </div>
      </div>
      <Footer />
    </>
  );
}