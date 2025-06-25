import { useContext } from 'react';
import { Navigate } from 'react-router-dom';
import { AuthContext } from '../context/AuthContext';

function PrivateRoute({ children }) {
  const { token } = useContext(AuthContext);

  // Redirect to login if no token
  if (!token) {
    return <Navigate to="/" />;
  }

  return children;
}

export default PrivateRoute;
