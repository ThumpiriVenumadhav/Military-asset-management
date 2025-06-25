import { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import { createTransfer, fetchTransfers } from '../services/transferService';
import { AuthContext } from '../context/AuthContext';

function TransfersPage() {
  const { token, userRole, user } = useContext(AuthContext);

  const [form, setForm] = useState({
    assetId: '',
    fromBaseId: '',
    toBaseId: '',
    quantity: ''
  });

  const [assets, setAssets] = useState([]);
  const [bases, setBases] = useState([]);
  const [inventory, setInventory] = useState(0);
  const [transfers, setTransfers] = useState([]);
  const [error, setError] = useState('');

  useEffect(() => {
    const loadMeta = async () => {
      try {
        const [assetRes, baseRes] = await Promise.all([
          axios.get('http://localhost:8080/api/assets', {
            headers: { Authorization: `Bearer ${token}` }
          }),
          axios.get('http://localhost:8080/api/bases', {
            headers: { Authorization: `Bearer ${token}` }
          }),
        ]);
        setAssets(assetRes.data);
        setBases(baseRes.data);
      } catch (err) {
        console.error('Error loading asset/base metadata', err);
      }
    };

    const loadTransfers = async () => {
      try {
        const data = await fetchTransfers(token);
        setTransfers(data);
      } catch (err) {
        console.error('Error fetching transfer history:', err.message);
      }
    };

    loadMeta();
    loadTransfers();
  }, [token]);

  const handleChange = async (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });

    // Trigger inventory check when asset or fromBase changes
    if ((name === 'assetId' && form.fromBaseId) || (name === 'fromBaseId' && form.assetId)) {
      const assetId = name === 'assetId' ? value : form.assetId;
      const fromBaseId = name === 'fromBaseId' ? value : form.fromBaseId;
      try {
        const res = await axios.get(`http://localhost:8080/api/assets/inventory`, {
          params: { baseId: fromBaseId, assetId },
          headers: { Authorization: `Bearer ${token}` }
        });
        setInventory(res.data.available || 0);
      } catch (err) {
        setInventory(0);
        console.error('Failed to fetch inventory:', err);
      }
    }
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    const { assetId, fromBaseId, toBaseId, quantity } = form;

    if (parseInt(quantity) > inventory) {
      setError(`Cannot transfer more than ${inventory} units`);
      return;
    }

    const payload = {
       assetId: parseInt(form.assetId),
       fromBaseId: parseInt(form.fromBaseId),
       toBaseId: parseInt(form.toBaseId),
       quantity: parseInt(form.quantity),
       transferDate: new Date().toISOString().slice(0, 10)  // format as YYYY-MM-DD
      };


      console.log('🚨 Payload being sent:', payload);
    try {
      await createTransfer(payload, token);
      setForm({ assetId: '', fromBaseId: '', toBaseId: '', quantity: '' });
      const updated = await fetchTransfers(token);
      setTransfers(updated);
      setInventory(0);
    } catch (err) {
      console.error('Transfer failed:', err.message);
      setError('Transfer failed. Check roles, stock, and permissions.');
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>Asset Transfers</h2>

      <form onSubmit={handleSubmit}>
        <label>
          Asset:
          <select name="assetId" value={form.assetId} onChange={handleChange} required>
            <option value="">-- Select --</option>
            {assets.map(a => (
              <option key={a.id} value={a.id}>{a.name}</option>
            ))}
          </select>
        </label>

        <label>
          From Base:
          <select name="fromBaseId" value={form.fromBaseId} onChange={handleChange} required>
            <option value="">-- Select --</option>
            {bases.map(b => (
              <option key={b.id} value={b.id}>{b.name}</option>
            ))}
          </select>
        </label>

        <label>
          To Base:
          <select name="toBaseId" value={form.toBaseId} onChange={handleChange} required>
            <option value="">-- Select --</option>
            {bases.map(b => (
              <option key={b.id} value={b.id}>{b.name}</option>
            ))}
          </select>
        </label>

        <label>
          Quantity (Available: {inventory}):
          <input type="number" name="quantity" value={form.quantity} onChange={handleChange} required />
        </label>

        <button type="submit">Submit Transfer</button>
        {error && <p style={{ color: 'red' }}>{error}</p>}
      </form>

      <h3>Transfer History</h3>
      <table border="1" cellPadding="5">
        <thead>
          <tr>
            <th>Asset</th>
            <th>From Base</th>
            <th>To Base</th>
            <th>Quantity</th>
            <th>Timestamp</th>
          </tr>
        </thead>
        <tbody>
          {transfers.map((t, idx) => (
            <tr key={idx}>
              <td>{t.asset?.name}</td>
              <td>{t.fromBase?.name}</td>
              <td>{t.toBase?.name}</td>
              <td>{t.quantity}</td>
              <td>{new Date(t.timestamp).toLocaleString()}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default TransfersPage;
