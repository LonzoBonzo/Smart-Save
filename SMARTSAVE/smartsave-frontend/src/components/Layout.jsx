import { Link, NavLink } from "react-router-dom";

export function Layout({ user, onLogout, children }) {
  return (
    <div className="app-shell">
      <header className="topbar">
        <Link to="/" className="brand">
          Smart Save
        </Link>
        <nav className="nav-links">
          <NavLink to="/">Dashboard</NavLink>
          <NavLink to="/data">Data Display</NavLink>
          <NavLink to="/form">Form Page</NavLink>
          {!user && <NavLink to="/login">Login</NavLink>}
          {!user && <NavLink to="/register">Register</NavLink>}
        </nav>
        <div className="topbar-user">
          {user ? (
            <>
              <span>{user.fullName}</span>
              <button className="secondary-button" onClick={onLogout}>
                Logout
              </button>
            </>
          ) : (
            <span>Guest mode</span>
          )}
        </div>
      </header>
      <main className="page-content">{children}</main>
    </div>
  );
}
