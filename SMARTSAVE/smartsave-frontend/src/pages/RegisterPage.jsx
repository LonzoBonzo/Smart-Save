import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";

export function RegisterPage({ onRegister }) {
  const navigate = useNavigate();
  const [form, setForm] = useState({ fullName: "", email: "", password: "" });
  const [error, setError] = useState("");

  async function handleSubmit(event) {
    event.preventDefault();
    try {
      setError("");
      await onRegister(form);
      navigate("/login");
    } catch (err) {
      setError(err.message);
    }
  }

  return (
    <section className="auth-shell">
      <form className="panel form-card auth-card" onSubmit={handleSubmit}>
        <p className="eyebrow">Create Account</p>
        <h1>Register</h1>
        <input
          placeholder="Full name"
          value={form.fullName}
          onChange={(event) => setForm({ ...form, fullName: event.target.value })}
          required
        />
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
          Register
        </button>
        <p className="status-text">
          Already registered? <Link to="/login">Login here</Link>
        </p>
      </form>
    </section>
  );
}
