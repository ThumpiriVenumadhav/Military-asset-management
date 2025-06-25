import { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import { AuthContext } from '../context/AuthContext';

function ExpendituresPage() {
  const { token } = useContext(AuthContext);
  const [assets, setAssets] = useState([]);
  const [bases, setBases] = useState([]);
  const [expenditures, setExpenditures] = useState([]);
  const [form, setForm] = useState({
    assetId: '',
    baseId: '',
    reason: '',
    date: new Date().toISOString().slice(0, 10),
  });
  const [error, setError] = useState('');

  useEffect(() => {
    fetchAssets();
    fetchBases();
    fetchExpenditures();
  }, []);

  const fetchAssets = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/assets', {
        headers: { Authorization: `Bearer ${token}` },
      });
      setAssets(res.data);
    } catch (err) {
      console.error('Failed to fetch assets:', err.message);
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

  const fetchExpenditures = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/expenditures', {
        headers: { Authorization: `Bearer ${token}` },
      });
      setExpenditures(res.data);
    } catch (err) {
      console.error('Failed to fetch expenditures:', err.message);
    }
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    const payload = {
      date: form.date,
      reason: form.reason,
      asset: { id: parseInt(form.assetId) },
      base: { id: parseInt(form.baseId) },
    };

    try {
      await axios.post('http://localhost:8080/api/expenditures', payload, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setForm({
        assetId: '',
        baseId: '',
        reason: '',
        date: new Date().toISOString().slice(0, 10),
      });
      fetchExpenditures();
    } catch (err) {
      console.error('Failed to create expenditure:', err.message);
      setError('Submission failed. Check that asset and base are selected.');
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>Log Expenditure</h2>

      <form onSubmit={handleSubmit}>
        <label>
          Asset:
          <select name="assetId" value={form.assetId} onChange={handleChange} required>
            <option value="">-- Select Asset --</option>
            {assets.map((a) => (
              <option key={a.id} value={a.id}>{a.name}</option>
            ))}
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

        <label>
          Reason:
          <input
            type="text"
            name="reason"
            value={form.reason}
            onChange={handleChange}
            required
          />
        </label>

        <label>
          Date:
          <input
            type="date"
            name="date"
            value={form.date}
            onChange={handleChange}
            required
          />
        </label>

        <button type="submit">Submit Expenditure</button>
        {error && <p style={{ color: 'red' }}>{error}</p>}
      </form>

      <h3>Expenditure Log</h3>
      <table border="1" cellPadding="5" style={{ borderCollapse: 'collapse' }}>
        <thead>
          <tr>
            <th>Asset</th>
            <th>Base</th>
            <th>Reason</th>
            <th>Date</th>
          </tr>
        </thead>
        <tbody>
          {expenditures.map((exp) => (
            <tr key={exp.id}>
              <td>{exp.asset?.name}</td>
              <td>{exp.base?.name || exp.asset?.base?.name || '-'}</td>
              <td>{exp.reason}</td>
              <td>{exp.date}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default ExpendituresPage;
