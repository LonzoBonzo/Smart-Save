import { useEffect } from "react";

export function DashboardPage({ dashboard, loading, loadDashboard, isGuest }) {
  useEffect(() => {
    loadDashboard();
  }, [loadDashboard]);

  return (
    <section className="page-grid">
      <div className="hero-card">
        <p className="eyebrow">Overview</p>
        <h1>Build safer habits with one secure dashboard.</h1>
        <p>
          Track savings goals, record transactions, and sync exchange-rate snapshots from the backend.
        </p>
        {isGuest && (
          <p className="guest-banner">
            Guest mode is active. You can preview the dashboard now and log in later when the backend is running.
          </p>
        )}
      </div>

      <div className="stats-grid">
        <article className="stat-card">
          <span>Goals</span>
          <strong>{dashboard?.goalsCount ?? "--"}</strong>
        </article>
        <article className="stat-card">
          <span>Transactions</span>
          <strong>{dashboard?.transactionsCount ?? "--"}</strong>
        </article>
        <article className="stat-card">
          <span>Total Saved</span>
          <strong>${dashboard?.totalSaved ?? "0.00"}</strong>
        </article>
        <article className="stat-card">
          <span>Income vs Expense</span>
          <strong>
            ${dashboard?.totalIncome ?? "0.00"} / ${dashboard?.totalExpenses ?? "0.00"}
          </strong>
        </article>
      </div>

      {loading && <p className="status-text">Loading dashboard data...</p>}
    </section>
  );
}
