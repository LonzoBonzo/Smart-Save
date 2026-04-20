import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export function LoginPage({ onLogin }) {
  const navigate = useNavigate();
  const [form, setForm] = useState({ email: "demo@smartsave.app", password: "Password123" });
  const [error, setError] = useState("");

  async function handleSubmit(event) {
    event.preventDefault();
    try {
      setError("");
      await onLogin(form);
      navigate("/");
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <section className="auth-shell">
      <form className="panel form-card auth-card" onSubmit={handleSubmit}>
        <p className="eyebrow">Session Authentication</p>
        <h1>Login</h1>
        <input
          type="email"
          placeholder="Email"
          value={form.email}
          onChange={(event) => setForm({ ...form, email: event.target.value })}
          required
        />
        <input
          type="password"
          placeholder="Password"
          value={form.password}
          onChange={(event) => setForm({ ...form, password: event.target.value })}
          required
        />
        {error && <p className="error-text">{error}</p>}
        <button className="primary-button" type="submit">
          Login
        </button>
        <p className="status-text">
          Need an account? <Link to="/register">Register here</Link>
        </p>
      </form>
    </section>
  );
}
