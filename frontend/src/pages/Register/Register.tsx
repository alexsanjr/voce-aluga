import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { register as registerService } from "../../services/registerService";
import NavBar from "../../components/NavBar/NavBar";
import Footer from "../../components/Footer/Footer";
import "./Register.css";
import bkgRegister from "../../assets/bkglogin3.jpg";
import { login } from "../../services/loginService";

// Função para aplicar máscara de CPF
function maskCPF(value: string) {
  return value
    .replace(/\D/g, "")
    .replace(/(\d{3})(\d)/, "$1.$2")
    .replace(/(\d{3})(\d)/, "$1.$2")
    .replace(/(\d{3})(\d{1,2})$/, "$1-$2")
    .slice(0, 14);
}

// Função para aplicar máscara de telefone
function maskPhone(value: string) {
  return value
    .replace(/\D/g, "")
    .replace(/^(\d{2})(\d)/g, "($1) $2")
    .replace(/(\d{5})(\d{1,4})$/, "$1-$2")
    .slice(0, 15);
}

export default function Register() {
  useEffect(() => {
    if (localStorage.getItem("token")) {
      window.location.replace("/aluguel");
    }
  }, []);

  const [nome, setNome] = useState("");
  const [documento, setDocumento] = useState("");
  const [dataNascimento, setDataNascimento] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [telefone, setTelefone] = useState("");
  const [error, setError] = useState("");
  const [success, setSuccess] = useState(false);
  const navigate = useNavigate();

  async function handleSubmit(e: React.FormEvent<HTMLFormElement>) {
    e.preventDefault();
    setError("");
    setSuccess(false);
    try {
      const usuario = {
        nome,
        documento: documento.replace(/\D/g, ""), // envia só números
        dataNascimento: dataNascimento,
        email,
        password,
        telefone: telefone.replace(/\D/g, ""), // envia só números
        tipo: "CLIENTE",
      };

      console.log("Registrando usuário:", usuario);

      await registerService(usuario);
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
      <div className="register-container" style={{ alignItems: "center", justifyContent: "center" }}>
        <div
          className="register-left"
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

        <div className="register-right" style={{ display: "flex", alignItems: "center", justifyContent: "center" }}>
          <div className="register-box">
            <h2>Crie sua conta</h2>
            <form onSubmit={handleSubmit} autoComplete="off">
              <div className="form-group">
                <label htmlFor="nome">Nome</label>
                <input
                  type="text"
                  id="nome"
                  placeholder="Digite seu nome completo"
                  required
                  value={nome}
                  onChange={e => setNome(e.target.value.replace(/[^A-Za-zÀ-ÿ\s]/g, ""))}
                  maxLength={60}
                />
              </div>
              <div className="form-group">
                <label htmlFor="documento">CPF</label>
                <input
                  type="text"
                  id="documento"
                  placeholder="CPF"
                  required
                  value={maskCPF(documento)}
                  onChange={e => setDocumento(maskCPF(e.target.value))}
                  maxLength={14}
                  inputMode="numeric"
                />
              </div>
              <div className="form-group">
                <label htmlFor="dataNascimento">Data de nascimento</label>
                <input
                  type="date"
                  id="dataNascimento"
                  required
                  value={dataNascimento}
                  onChange={e => setDataNascimento(e.target.value)}
                  max="2020-12-31"
                  min="1900-01-01"
                />
              </div>
              <div className="form-group">
                <label htmlFor="telefone">Telefone</label>
                <input
                  type="tel"
                  id="telefone"
                  placeholder="Digite seu telefone"
                  required
                  value={maskPhone(telefone)}
                  onChange={e => setTelefone(maskPhone(e.target.value))}
                  maxLength={15}
                  inputMode="numeric"
                />
              </div>
              <div className="form-group">
                <label htmlFor="email">E-mail</label>
                <input
                  type="email"
                  id="email"
                  placeholder="Digite seu e-mail"
                  required
                  value={email}
                  onChange={e => setEmail(e.target.value.replace(/[^\w@.\-]/g, ""))}
                  maxLength={60}
                  autoComplete="email"
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
                  onChange={e => setPassword(e.target.value.replace(/\s/g, ""))}
                  minLength={6}
                  maxLength={32}
                  autoComplete="new-password"
                />
              </div>

              {error && <div style={{ color: "#c00", marginBottom: 8 }}>{error}</div>}
              {success && <div style={{ color: "#080", marginBottom: 8 }}>Cadastro realizado! Redirecionando...</div>}

              <button type="submit" className="register-button">
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
