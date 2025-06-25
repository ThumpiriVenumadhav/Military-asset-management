import { useState, useContext } from 'react';
import { useNavigate } from 'react-router-dom';
import { loginUser } from '../services/authService';
import { AuthContext } from '../context/AuthContext';

function LoginPage() {
  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');
  const [error, setError] = useState('');

  const navigate = useNavigate();
  const { login } = useContext(AuthContext); // login method from context

  const handleLogin = async (e) => {
  e.preventDefault();
  setError('');

  try {
    const data = await loginUser(username, password);
    console.log("Login response:", data);

    // Since backend returns a raw string token (not a JSON object)
    const jwt = typeof data === 'string' ? data : data.token || data.jwt;

    if (!jwt || !jwt.includes('.')) {
      throw new Error("Invalid or missing token");
    }

    localStorage.setItem('token', jwt);
    login(jwt); // <-- updates context
    navigate('/dashboard');
  } catch (err) {
    console.error('Login failed:', err.message);
    setError('Invalid credentials or token issue');
  }
};


  return (
    <div style={{ maxWidth: '400px', margin: '40px auto', padding: '20px', border: '1px solid #ccc' }}>
      <h2 style={{ textAlign: 'center' }}>Login</h2>
      <form onSubmit={handleLogin}>
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
          style={{ width: '100%', padding: '8px', marginBottom: '10px' }}
        />

        <input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
          style={{ width: '100%', padding: '8px', marginBottom: '10px' }}
        />

        <button
          type="submit"
          style={{ width: '100%', padding: '10px', backgroundColor: '#0a69b8', color: '#fff', border: 'none' }}
        >
          Login
        </button>

        {error && <p style={{ color: 'red', marginTop: '10px' }}>{error}</p>}
      </form>
    </div>
  );
}

export default LoginPage;
