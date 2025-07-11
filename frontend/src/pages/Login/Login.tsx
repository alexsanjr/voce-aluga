import "./Login.css";
import bkgLogin from "../../assets/bkglogin1.webp"; // coloque aqui a imagem desejada (ex: cidade, carro, etc.)
import logo from "../../assets/logo_footer.png";

export default function Login() {
  return (
    <div className="login-container">
      <div
        className="login-left"
        style={{ backgroundImage: `url(${bkgLogin})` }}
      >
        <div className="left-overlay">
          <a href="/">
            <img src={logo} alt="Logo" className="login-logo" />
          </a>
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
          <h2>Acesse sua conta</h2>
          <form>
            <div className="form-group">
              <label htmlFor="email">E-mail</label>
              <input
                type="email"
                id="email"
                placeholder="Digite seu e-mail"
                required
              />
            </div>

            <div className="form-group">
              <label htmlFor="password">Senha</label>
              <input
                type="password"
                id="password"
                placeholder="Digite sua senha"
                required
              />
            </div>

            <button type="submit" className="login-button">
              Entrar
            </button>
          </form>

          <p className="signup-link">
            Não tem uma conta? <a href="/register" className="redirect-cadastrar">Cadastre-se</a>
          </p>
        </div>
      </div>
    </div>
  );
}
