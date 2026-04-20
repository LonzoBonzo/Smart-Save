import { Navigate } from "react-router-dom";

export function ProtectedRoute({ user, sessionChecked, children }) {
  if (!sessionChecked) {
    return <p className="status-text">Checking your session...</p>;
  }
  if (!user) {
    return <Navigate to="/login" replace />;
  }
  return children;
}
