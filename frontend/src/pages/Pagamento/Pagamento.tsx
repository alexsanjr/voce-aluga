import { useState } from "react";
import type { ChangeEvent, FormEvent } from "react";
import "./Pagamento.css";

export default function Pagamento() {
  const [metodo, setMetodo] = useState("");
  const [loading, setLoading] = useState(false);
  const [form, setForm] = useState({
    cardNumber: "",
    cardExpiry: "",
    cardCVV: "",
    cardName: "",
  });

  function handleInput(e: ChangeEvent<HTMLInputElement>) {
    setForm({ ...form, [e.target.id]: e.target.value });
  }

  async function handleSubmit(e: FormEvent<HTMLFormElement>) {
    e.preventDefault();
    setLoading(true);
    let payload: any = { metodo };
    if (metodo === "credit-card" || metodo === "debit-card") {
      payload = {
        ...payload,
        cardNumber: form.cardNumber,
        cardExpiry: form.cardExpiry,
        cardCVV: form.cardCVV,
        cardName: form.cardName,
      };
    }
    try {
      const response = await fetch("http://localhost:8080/pagamento", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload),
      });

      console.log(response);
      // Aqui você pode redirecionar ou mostrar mensagem de sucesso
    } catch (err) {
      // Trate o erro se necessário
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="pagamento-container">
      <div className="pagamento-box">
        <h2>Pagamento</h2>
        <p className="pagamento-desc">Selecione o método de pagamento:</p>
        <form onSubmit={handleSubmit}>
          <div className="payment-options">
            <label className="payment-option">
              <input
                type="radio"
                name="payment-method"
                value="credit-card"
                checked={metodo === "credit-card"}
                onChange={() => setMetodo("credit-card")}
                disabled={loading}
              />
              <span>Cartão de Crédito</span>
            </label>
            <label className="payment-option">
              <input
                type="radio"
                name="payment-method"
                value="debit-card"
                checked={metodo === "debit-card"}
                onChange={() => setMetodo("debit-card")}
                disabled={loading}
              />
              <span>Cartão de Débito</span>
            </label>
            <label className="payment-option">
              <input
                type="radio"
                name="payment-method"
                value="pix"
                checked={metodo === "pix"}
                onChange={() => setMetodo("pix")}
                disabled={loading}
              />
              <span>PIX</span>
            </label>
          </div>

          {(metodo === "credit-card" || metodo === "debit-card") && (
            <div className="payment-fields">
              <div className="form-group">
                <label htmlFor="cardNumber">Número do cartão</label>
                <input
                  type="text"
                  id="cardNumber"
                  placeholder="0000 0000 0000 0000"
                  maxLength={19}
                  required
                  value={form.cardNumber}
                  onChange={handleInput}
                  disabled={loading}
                />
              </div>
              <div className="form-row">
                <div className="form-group">
                  <label htmlFor="cardExpiry">Validade</label>
                  <input
                    type="text"
                    id="cardExpiry"
                    placeholder="MM/AA"
                    maxLength={5}
                    required
                    value={form.cardExpiry}
                    onChange={handleInput}
                    disabled={loading}
                  />
                </div>
                <div className="form-group">
                  <label htmlFor="cardCVV">CVV</label>
                  <input
                    type="password"
                    id="cardCVV"
                    placeholder="CVV"
                    maxLength={4}
                    required
                    value={form.cardCVV}
                    onChange={handleInput}
                    disabled={loading}
                  />
                </div>
              </div>
              <div className="form-group">
                <label htmlFor="cardName">Nome impresso no cartão</label>
                <input
                  type="text"
                  id="cardName"
                  placeholder="Nome completo"
                  required
                  value={form.cardName}
                  onChange={handleInput}
                  disabled={loading}
                />
              </div>
            </div>
          )}

          {metodo === "pix" && (
            <div className="payment-fields">
              <div className="form-group">
                <label>Chave PIX</label>
                <input type="text" value="empresa@exemplo.com" disabled />
              </div>
              <div className="pix-info">
                Abra seu app bancário e pague usando a chave acima.
              </div>
            </div>
          )}

          <button type="submit" className="pagamento-button" disabled={loading}>
            {loading ? <span className="loader"></span> : "Confirmar Pagamento"}
          </button>
        </form>
      </div>
    </div>
  );
}