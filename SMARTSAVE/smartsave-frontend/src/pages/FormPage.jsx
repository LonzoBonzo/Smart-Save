import { useState } from "react";
import { Link } from "react-router-dom";

const defaultGoal = {
  name: "",
  targetAmount: "",
  currentAmount: "",
  targetDate: "",
};

const defaultTransaction = {
  type: "EXPENSE",
  amount: "",
  description: "",
  transactionDate: "",
  tags: "Savings",
};

export function FormPage({ onCreateGoal, onCreateTransaction, formMessage, isGuest }) {
  const [goalForm, setGoalForm] = useState(defaultGoal);
  const [transactionForm, setTransactionForm] = useState(defaultTransaction);

  async function handleGoalSubmit(event) {
    event.preventDefault();
    await onCreateGoal({
      ...goalForm,
      targetAmount: Number(goalForm.targetAmount),
      currentAmount: goalForm.currentAmount ? Number(goalForm.currentAmount) : 0,
      targetDate: goalForm.targetDate || null,
    });
    setGoalForm(defaultGoal);
  }

  async function handleTransactionSubmit(event) {
    event.preventDefault();
    await onCreateTransaction({
      ...transactionForm,
      amount: Number(transactionForm.amount),
      tags: transactionForm.tags
        .split(",")
        .map((tag) => tag.trim())
        .filter(Boolean),
    });
    setTransactionForm(defaultTransaction);
  }

  return (
    <section className="page-grid">
      <div className="section-heading">
        <div>
          <p className="eyebrow">Backend POST Requests</p>
          <h2>Secure form page</h2>
        </div>
        {formMessage && <p className="status-text">{formMessage}</p>}
      </div>

      {isGuest && (
        <article className="panel">
          <p className="guest-banner">
            Guest mode can preview this page, but saving goals and transactions requires login and a running backend.
            <Link to="/login"> Log in when you are ready.</Link>
          </p>
        </article>
      )}

      <div className="card-grid">
        <form className="panel form-card" onSubmit={handleGoalSubmit}>
          <h3>Create Savings Goal</h3>
          <input
            placeholder="Goal name"
            value={goalForm.name}
            onChange={(event) => setGoalForm({ ...goalForm, name: event.target.value })}
            disabled={isGuest}
            required
          />
          <input
            placeholder="Target amount"
            type="number"
            min="0.01"
            step="0.01"
            value={goalForm.targetAmount}
            onChange={(event) => setGoalForm({ ...goalForm, targetAmount: event.target.value })}
            disabled={isGuest}
            required
          />
          <input
            placeholder="Current amount"
            type="number"
            min="0"
            step="0.01"
            value={goalForm.currentAmount}
            onChange={(event) => setGoalForm({ ...goalForm, currentAmount: event.target.value })}
            disabled={isGuest}
          />
          <input
            type="date"
            value={goalForm.targetDate}
            onChange={(event) => setGoalForm({ ...goalForm, targetDate: event.target.value })}
            disabled={isGuest}
          />
          <button className="primary-button" type="submit" disabled={isGuest}>
            Save Goal
          </button>
        </form>

        <form className="panel form-card" onSubmit={handleTransactionSubmit}>
          <h3>Create Transaction</h3>
          <select
            value={transactionForm.type}
            onChange={(event) => setTransactionForm({ ...transactionForm, type: event.target.value })}
            disabled={isGuest}
          >
            <option value="EXPENSE">Expense</option>
            <option value="INCOME">Income</option>
          </select>
          <input
            placeholder="Amount"
            type="number"
            min="0.01"
            step="0.01"
            value={transactionForm.amount}
            onChange={(event) => setTransactionForm({ ...transactionForm, amount: event.target.value })}
            disabled={isGuest}
            required
          />
          <input
            placeholder="Description"
            value={transactionForm.description}
            onChange={(event) => setTransactionForm({ ...transactionForm, description: event.target.value })}
            disabled={isGuest}
            required
          />
          <input
            type="date"
            value={transactionForm.transactionDate}
            onChange={(event) =>
              setTransactionForm({ ...transactionForm, transactionDate: event.target.value })
            }
            disabled={isGuest}
            required
          />
          <input
            placeholder="Comma-separated tags"
            value={transactionForm.tags}
            onChange={(event) => setTransactionForm({ ...transactionForm, tags: event.target.value })}
            disabled={isGuest}
            required
          />
          <button className="primary-button" type="submit" disabled={isGuest}>
            Save Transaction
          </button>
        </form>
      </div>
    </section>
  );
}
