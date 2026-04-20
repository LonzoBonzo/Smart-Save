import { useEffect } from "react";

export function DataDisplayPage({
  goals,
  transactions,
  rates,
  tags,
  error,
  loading,
  loadData,
  onRefreshRates,
  isGuest,
}) {
  useEffect(() => {
    loadData();
  }, [loadData]);

  return (
    <section className="page-grid">
      <div className="section-heading">
        <div>
          <p className="eyebrow">Backend GET Requests</p>
          <h2>Data display page</h2>
        </div>
        <button className="primary-button" onClick={onRefreshRates} disabled={isGuest}>
          Refresh API Data
        </button>
      </div>

      {isGuest && (
        <p className="guest-banner">
          You are viewing sample finance data. Start the backend and log in to use live GET requests and API refresh.
        </p>
      )}
      {loading && <p className="status-text">Loading protected data...</p>}
      {error && <p className="error-text">{error}</p>}

      <div className="card-grid">
        <article className="panel">
          <h3>Savings Goals</h3>
          {goals.map((goal) => (
            <div key={goal.id} className="list-row">
              <span>{goal.name}</span>
              <span>
                ${goal.currentAmount} / ${goal.targetAmount}
              </span>
            </div>
          ))}
        </article>

        <article className="panel">
          <h3>Transactions</h3>
          {transactions.map((transaction) => (
            <div key={transaction.id} className="list-row">
              <span>
                {transaction.description} <small>({transaction.type})</small>
              </span>
              <span>${transaction.amount}</span>
            </div>
          ))}
        </article>

        <article className="panel">
          <h3>Exchange Rates</h3>
          {rates.length === 0 ? (
            <p className="status-text">No external API data stored yet. Use Refresh API Data.</p>
          ) : (
            rates.map((rate) => (
              <div key={rate.id} className="list-row">
                <span>
                  {rate.baseCurrency} to {rate.targetCurrency}
                </span>
                <span>{rate.rate}</span>
              </div>
            ))
          )}
        </article>
      </div>

      <article className="panel">
        <h3>Available Tags</h3>
        <div className="tag-wrap">
          {tags.map((tag) => (
            <span className="tag-chip" key={tag}>
              {tag}
            </span>
          ))}
        </div>
      </article>
    </section>
  );
}
