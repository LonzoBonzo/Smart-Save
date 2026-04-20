const defaultHeaders = {
  "Content-Type": "application/json",
};

async function request(path, options = {}) {
  const response = await fetch(path, {
    credentials: "include",
    headers: {
      ...defaultHeaders,
      ...(options.headers || {}),
    },
    ...options,
  });

  const contentType = response.headers.get("content-type") || "";
  const payload = contentType.includes("application/json")
    ? await response.json()
    : await response.text();

  if (!response.ok) {
    const message =
      typeof payload === "object" && payload !== null
        ? payload.message || "Request failed"
        : "Request failed";
    throw new Error(message);
  }

  return payload;
}

export const api = {
  getSession: () => request("/api/auth/me"),
  register: (body) => request("/api/auth/register", { method: "POST", body: JSON.stringify(body) }),
  login: (body) => request("/api/auth/login", { method: "POST", body: JSON.stringify(body) }),
  logout: () => request("/api/auth/logout", { method: "POST" }),
  getDashboard: () => request("/api/dashboard"),
  getGoals: () => request("/api/goals"),
  createGoal: (body) => request("/api/goals", { method: "POST", body: JSON.stringify(body) }),
  getTransactions: () => request("/api/transactions"),
  getTags: () => request("/api/transactions/tags"),
  createTransaction: (body) => request("/api/transactions", { method: "POST", body: JSON.stringify(body) }),
  getRates: () => request("/api/external/rates"),
  refreshRates: () => request("/api/external/rates/refresh", { method: "POST" }),
};
