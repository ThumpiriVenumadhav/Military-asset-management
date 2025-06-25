import { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import { AuthContext } from '../context/AuthContext';

function UsersPage() {
  const { token } = useContext(AuthContext);
  const [users, setUsers] = useState([]);
  const [allUsers, setAllUsers] = useState([]);
  const [bases, setBases] = useState([]);
  const [form, setForm] = useState({
    username: '',
    password: '',
    role: '',
    baseId: '',
  });
  const [filters, setFilters] = useState({
    username: '',
    role: '',
    baseId: '',
  });
  const [error, setError] = useState('');
  const roles = ['ADMIN', 'COMMANDER', 'LOGISTICS'];

  useEffect(() => {
    fetchUsers();
    fetchBases();
  }, []);

  const fetchUsers = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/users', {
        headers: { Authorization: `Bearer ${token}` },
      });
      setAllUsers(res.data);
      setUsers(res.data);
    } catch (err) {
      console.error('Failed to fetch users:', err.message);
    }
  };

  const fetchBases = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/bases', {
        headers: { Authorization: `Bearer ${token}` },
      });
      setBases(res.data);
    } catch (err) {
      console.error('Failed to fetch bases:', err.message);
    }
  };

  const handleFormChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleFilterChange = (e) => {
    const { name, value } = e.target;
    const updated = { ...filters, [name]: value };
    setFilters(updated);
  };

  const applyFilters = () => {
    const { username, role, baseId } = filters;
    const filtered = allUsers.filter((u) => {
      const matchUsername = !username || u.username.toLowerCase().includes(username.toLowerCase());
      const matchRole = !role || u.role === role;
      const matchBase = !baseId || u.base?.id?.toString() === baseId;
      return matchUsername && matchRole && matchBase;
    });
    setUsers(filtered);
  };

  const clearFilters = () => {
    setFilters({ username: '', role: '', baseId: '' });
    setUsers(allUsers);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    const payload = {
      username: form.username,
      password: form.password,
      role: form.role,
      base: { id: parseInt(form.baseId) },
    };

    try {
      await axios.post('http://localhost:8080/api/users', payload, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setForm({ username: '', password: '', role: '', baseId: '' });
      fetchUsers();
    } catch (err) {
      console.error('User creation failed:', err.message);
      setError('Failed to create user.');
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>Create User</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Username:
          <input name="username" value={form.username} onChange={handleFormChange} required />
        </label>

        <label>
          Password:
          <input type="password" name="password" value={form.password} onChange={handleFormChange} required />
        </label>

        <label>
          Role:
          <select name="role" value={form.role} onChange={handleFormChange} required>
            <option value="">-- Select Role --</option>
            {roles.map((r) => (
              <option key={r} value={r}>{r}</option>
            ))}
          </select>
        </label>

        <label>
          Base:
          <select name="baseId" value={form.baseId} onChange={handleFormChange} required>
            <option value="">-- Select Base --</option>
            {bases.map((b) => (
              <option key={b.id} value={b.id}>{b.name}</option>
            ))}
          </select>
        </label>

        <button type="submit">Create User</button>
        {error && <p style={{ color: 'red' }}>{error}</p>}
      </form>

      <h3>Filter Users</h3>
      <div style={{ marginBottom: '15px' }}>
        <input
          type="text"
          name="username"
          value={filters.username}
          onChange={handleFilterChange}
          placeholder="Search username"
        />

        <select name="role" value={filters.role} onChange={handleFilterChange}>
          <option value="">-- Role --</option>
          {roles.map((r) => (
            <option key={r} value={r}>{r}</option>
          ))}
        </select>

        <select name="baseId" value={filters.baseId} onChange={handleFilterChange}>
          <option value="">-- Base --</option>
          {bases.map((b) => (
            <option key={b.id} value={b.id}>{b.name}</option>
          ))}
        </select>

        <button onClick={applyFilters}>Apply</button>
        <button onClick={clearFilters} style={{ marginLeft: '10px' }}>
          Reset
        </button>
      </div>

      <h3>All Users</h3>
      <table border="1" cellPadding="5" style={{ borderCollapse: 'collapse' }}>
        <thead>
          <tr>
            <th>Username</th>
            <th>Role</th>
            <th>Base</th>
          </tr>
        </thead>
        <tbody>
          {users.map((u) => (
            <tr key={u.id}>
              <td>{u.username}</td>
              <td>{u.role}</td>
              <td>{u.base?.name || '-'}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default UsersPage;
