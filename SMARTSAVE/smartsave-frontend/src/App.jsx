import { useCallback, useEffect, useReducer } from "react";
import { Route, Routes } from "react-router-dom";
import { api } from "./api";
import { Layout } from "./components/Layout";
import { DashboardPage } from "./pages/DashboardPage";
import { DataDisplayPage } from "./pages/DataDisplayPage";
import { FormPage } from "./pages/FormPage";
import { LoginPage } from "./pages/LoginPage";
import { RegisterPage } from "./pages/RegisterPage";

const guestDashboard = {
  userName: "Guest Saver",
  goalsCount: 3,
  transactionsCount: 5,
  totalSaved: "1280.00",
  totalIncome: "2150.00",
  totalExpenses: "870.00",
};

const guestData = {
  goals: [
    { id: 1, name: "Emergency Fund", currentAmount: "650.00", targetAmount: "1500.00" },
    { id: 2, name: "Semester Books", currentAmount: "220.00", targetAmount: "400.00" },
    { id: 3, name: "Summer Travel", currentAmount: "410.00", targetAmount: "900.00" },
  ],
  transactions: [
    { id: 1, description: "Campus job paycheck", type: "INCOME", amount: "850.00" },
    { id: 2, description: "Weekly groceries", type: "EXPENSE", amount: "62.45" },
    { id: 3, description: "Savings transfer", type: "INCOME", amount: "200.00" },
  ],
  tags: ["Budget", "Food", "Groceries", "Paycheck", "Savings"],
  rates: [
    { id: 1, baseCurrency: "USD", targetCurrency: "EUR", rate: "0.91" },
    { id: 2, baseCurrency: "USD", targetCurrency: "GBP", rate: "0.78" },
    { id: 3, baseCurrency: "USD", targetCurrency: "CAD", rate: "1.37" },
  ],
};

const initialState = {
  user: null,
  sessionChecked: false,
  dashboard: null,
  goals: [],
  transactions: [],
  tags: [],
  rates: [],
  loading: false,
  error: "",
  formMessage: "",
};

function reducer(state, action) {
  switch (action.type) {
    case "SET_LOADING":
      return { ...state, loading: action.payload };
    case "SET_USER":
      return { ...state, user: action.payload };
    case "SET_SESSION_CHECKED":
      return { ...state, sessionChecked: action.payload };
    case "SET_ERROR":
      return { ...state, error: action.payload };
    case "SET_DASHBOARD":
      return { ...state, dashboard: action.payload };
    case "SET_DATA":
      return {
        ...state,
        goals: action.payload.goals,
        transactions: action.payload.transactions,
        tags: action.payload.tags,
        rates: action.payload.rates,
      };
    case "SET_FORM_MESSAGE":
      return { ...state, formMessage: action.payload };
    default:
      return state;
  }
}

export default function App() {
  const [state, dispatch] = useReducer(reducer, initialState);

  const loadSession = useCallback(async () => {
    try {
      const response = await api.getSession();
      dispatch({ type: "SET_USER", payload: response.user });
    } catch {
      dispatch({ type: "SET_USER", payload: null });
    } finally {
      dispatch({ type: "SET_SESSION_CHECKED", payload: true });
    }
  }, []);

  const loadDashboard = useCallback(async () => {
    if (!state.user) {
      dispatch({ type: "SET_DASHBOARD", payload: guestDashboard });
      return;
    }
    dispatch({ type: "SET_LOADING", payload: true });
    try {
      const dashboard = await api.getDashboard();
      dispatch({ type: "SET_DASHBOARD", payload: dashboard });
    } catch (error) {
      dispatch({ type: "SET_ERROR", payload: error.message });
    } finally {
      dispatch({ type: "SET_LOADING", payload: false });
    }
  }, [state.user]);

  const loadData = useCallback(async () => {
    if (!state.user) {
      dispatch({
        type: "SET_DATA",
        payload: guestData,
      });
      dispatch({
        type: "SET_ERROR",
        payload: "Guest mode is showing sample financial data. Log in after the backend is running to load live records.",
      });
      return;
    }
    dispatch({ type: "SET_LOADING", payload: true });
    dispatch({ type: "SET_ERROR", payload: "" });
    try {
      const [goals, transactions, tags, rates] = await Promise.all([
        api.getGoals(),
        api.getTransactions(),
        api.getTags(),
        api.getRates(),
      ]);
      dispatch({
        type: "SET_DATA",
        payload: { goals, transactions, tags, rates },
      });
    } catch (error) {
      dispatch({ type: "SET_ERROR", payload: error.message });
    } finally {
      dispatch({ type: "SET_LOADING", payload: false });
    }
  }, [state.user]);

  useEffect(() => {
    loadSession();
  }, [loadSession]);

  async function handleRegister(form) {
    return api.register(form);
  }

  async function handleLogin(form) {
    const response = await api.login(form);
    dispatch({ type: "SET_USER", payload: response.user });
    dispatch({ type: "SET_ERROR", payload: "" });
  }

  async function handleLogout() {
    try {
      await api.logout();
    } catch {
      // Allow guest fallback even if the backend is offline.
    }
    dispatch({ type: "SET_USER", payload: null });
    dispatch({ type: "SET_DASHBOARD", payload: guestDashboard });
    dispatch({ type: "SET_DATA", payload: guestData });
    dispatch({
      type: "SET_ERROR",
      payload: "You are viewing guest mode with sample financial data.",
    });
  }

  async function handleCreateGoal(form) {
    await api.createGoal(form);
    dispatch({ type: "SET_FORM_MESSAGE", payload: "Savings goal saved successfully." });
    await Promise.all([loadDashboard(), loadData()]);
  }

  async function handleCreateTransaction(form) {
    await api.createTransaction(form);
    dispatch({ type: "SET_FORM_MESSAGE", payload: "Transaction saved successfully." });
    await Promise.all([loadDashboard(), loadData()]);
  }

  async function handleRefreshRates() {
    dispatch({ type: "SET_ERROR", payload: "" });
    try {
      const response = await api.refreshRates();
      dispatch({ type: "SET_FORM_MESSAGE", payload: response.message });
      await loadData();
    } catch (error) {
      dispatch({ type: "SET_ERROR", payload: error.message });
    }
  }

  return (
    <Layout user={state.user} onLogout={handleLogout}>
      <Routes>
        <Route
          path="/"
          element={
            <DashboardPage
              dashboard={state.dashboard}
              loading={state.loading}
              loadDashboard={loadDashboard}
              isGuest={!state.user}
            />
          }
        />
        <Route
          path="/data"
          element={
            <DataDisplayPage
              goals={state.goals}
              transactions={state.transactions}
              tags={state.tags}
              rates={state.rates}
              error={state.error}
              loading={state.loading}
              loadData={loadData}
              onRefreshRates={handleRefreshRates}
              isGuest={!state.user}
            />
          }
        />
        <Route
          path="/form"
          element={
            <FormPage
              onCreateGoal={handleCreateGoal}
              onCreateTransaction={handleCreateTransaction}
              formMessage={state.formMessage}
              isGuest={!state.user}
            />
          }
        />
        <Route path="/login" element={<LoginPage onLogin={handleLogin} />} />
        <Route path="/register" element={<RegisterPage onRegister={handleRegister} />} />
      </Routes>
    </Layout>
  );
}
