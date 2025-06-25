import { createContext, useState, useEffect } from 'react';
import {jwtDecode } from 'jwt-decode';

export const AuthContext = createContext();

export function AuthProvider({ children }) {
  const [token, setToken] = useState(localStorage.getItem('token'));
  const [userRole, setUserRole] = useState('');

  useEffect(() => {
  if (token) {
    const parts = token.split('.');
    if (parts.length === 3) {
      try {
        const decoded = jwtDecode(token);
        setUserRole(decoded.role); // Or adapt to your claim name like decoded.authorities
      } catch (err) {
        console.error("Invalid token:", err.message);
        setUserRole('');
      }
    } else {
      console.warn("Token is not properly formatted.");
      setUserRole('');
    }
  } else {
    setUserRole('');
  }
}, [token]);

  const login = (newToken) => {
    localStorage.setItem('token', newToken);
    setToken(newToken);
  };

  const logout = () => {
    localStorage.removeItem('token');
    setToken(null);
    setUserRole('');
  };

  return (
    <AuthContext.Provider value={{ token, userRole, login, logout }}>
      {children}
    </AuthContext.Provider>
  );
}



