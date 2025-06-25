import { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import { AuthContext } from '../context/AuthContext';

function AssetsPage() {
  const { token } = useContext(AuthContext);
  const [assets, setAssets] = useState([]);
  const [bases, setBases] = useState([]);
  const [form, setForm] = useState({
    name: '',
    type: '',
    serialNumber: '',
    quantity: '',
    status: 'AVAILABLE',
    baseId: '',
  });
  const [error, setError] = useState('');

  useEffect(() => {
    fetchAssets();
    fetchBases();
  }, []);

  const fetchAssets = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/assets', {
        headers: { Authorization: `Bearer ${token}` },
      });
      setAssets(res.data);
    } catch (err) {
      console.error('Error fetching assets:', err.message);
      setError('Unable to fetch assets.');
    }
  };

  const fetchBases = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/bases', {
        headers: { Authorization: `Bearer ${token}` },
      });
      setBases(res.data);
    } catch (err) {
      console.error('Error fetching bases:', err.message);
      setError('Unable to load bases.');
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    if (!form.baseId) {
      setError('Base must be selected.');
      return;
    }

    const payload = {
      name: form.name,
      type: form.type,
      serialNumber: form.serialNumber,
      quantity: parseInt(form.quantity),
      status: form.status,
      base: { id: parseInt(form.baseId) },
    };

    try {
      await axios.post('http://localhost:8080/api/assets', payload, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setForm({
        name: '',
        type: '',
        serialNumber: '',
        quantity: '',
        status: 'AVAILABLE',
        baseId: '',
      });
      fetchAssets();
    } catch (err) {
      console.error('Asset creation failed:', err.message);
      setError('Failed to create asset.');
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>Assets</h2>

      <form onSubmit={handleSubmit}>
        <label>
          Name:
          <input name="name" value={form.name} onChange={handleChange} required />
        </label>

        <label>
          Type:
          <input name="type" value={form.type} onChange={handleChange} required />
        </label>

        <label>
          Serial Number:
          <input name="serialNumber" value={form.serialNumber} onChange={handleChange} required />
        </label>

        <label>
          Quantity:
          <input
            type="number"
            name="quantity"
            value={form.quantity}
            onChange={handleChange}
            min="1"
            required
          />
        </label>

        <label>
          Status:
          <select name="status" value={form.status} onChange={handleChange}>
            <option value="AVAILABLE">AVAILABLE</option>
            <option value="UNAVAILABLE">UNAVAILABLE</option>
          </select>
        </label>

        <label>
          Base:
          <select name="baseId" value={form.baseId} onChange={handleChange} required>
            <option value="">-- Select Base --</option>
            {bases.map((b) => (
              <option key={b.id} value={b.id}>{b.name}</option>
            ))}
          </select>
        </label>

        <button type="submit">Add Asset</button>
        {error && <p style={{ color: 'red' }}>{error}</p>}
      </form>

      <h3>Asset List</h3>
      <table border="1" cellPadding="5" style={{ borderCollapse: 'collapse' }}>
        <thead>
          <tr>
            <th>Name</th>
            <th>Type</th>
            <th>Serial #</th>
            <th>Quantity</th>
            <th>Status</th>
            <th>Base</th>
          </tr>
        </thead>
        <tbody>
          {assets.map((a) => (
            <tr key={a.id}>
              <td>{a.name}</td>
              <td>{a.type}</td>
              <td>{a.serialNumber}</td>
              <td>{a.quantity}</td>
              <td>{a.status}</td>
              <td>{a.base?.name || '-'}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default AssetsPage;
