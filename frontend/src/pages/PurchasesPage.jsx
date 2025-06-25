import { useState, useEffect, useContext } from 'react';
import { createPurchase, fetchPurchases } from '../services/purchaseService';
import axios from 'axios';
import { AuthContext } from '../context/AuthContext';

function PurchasesPage() {
  const { token } = useContext(AuthContext);
  const [form, setForm] = useState({
    assetId: '',
    baseId: '',
    quantity: ''
  });

  const [assets, setAssets] = useState([]);
  const [bases, setBases] = useState([]);
  const [purchases, setPurchases] = useState([]);
  const [error, setError] = useState('');

  // Fetch bases and assets for dropdowns
 useEffect(() => {
  const loadMeta = async () => {
    if (!token) return;

    try {
      console.log('Fetching assets and bases...');
      
      const [resAssets, resBases] = await Promise.all([
        axios.get('http://localhost:8080/api/assets', {
          headers: { Authorization: `Bearer ${token}` },
        }),
        axios.get('http://localhost:8080/api/bases', {
          headers: { Authorization: `Bearer ${token}` },
        }),
      ]);

      setAssets(resAssets.data);
      setBases(resBases.data);
    } catch (err) {
      console.error('Failed to fetch meta:', err?.response?.data || err.message);
    }
  };

  loadMeta();
}, [token]);


  // Fetch purchase history
  useEffect(() => {
    const loadPurchases = async () => {
      try {
        const data = await fetchPurchases(token);
        setPurchases(data);
      } catch (err) {
        console.error('Failed to fetch purchases:', err.message);
      }
    };

    loadPurchases();
  }, [token]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    const payload = {
      asset: { id: parseInt(form.assetId) },
      base: { id: parseInt(form.baseId) },
      quantity: parseInt(form.quantity),
      date: new Date().toISOString().split('T')[0]
    };

    try {
      await createPurchase(payload, token);
      setForm({ assetId: '', baseId: '', quantity: '' });
      const updated = await fetchPurchases(token);
      setPurchases(updated);
    } catch (err) {
      console.error('Purchase failed:', err.message);
      setError('Failed to record purchase. Please check values and permissions.');
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>Record New Purchase</h2>
      <form onSubmit={handleSubmit} style={{ marginBottom: '20px' }}>
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
          Base:
          <select name="baseId" value={form.baseId} onChange={handleChange} required>
            <option value="">-- Select --</option>
            {bases.map(b => (
              <option key={b.id} value={b.id}>{b.name}</option>
            ))}
          </select>
        </label>

        <label>
          Quantity:
          <input type="number" name="quantity" value={form.quantity} onChange={handleChange} required />
        </label>

        <button type="submit">Submit Purchase</button>
        {error && <p style={{ color: 'red' }}>{error}</p>}
      </form>

      <h3>Purchase History</h3>
      <table border="1" cellPadding="5">
        <thead>
          <tr>
            <th>Asset</th>
            <th>Base</th>
            <th>Quantity</th>
            <th>Date</th>
          </tr>
        </thead>
        <tbody>
          {purchases.map((p, idx) => (
            <tr key={idx}>
              <td>{p.asset?.name}</td>
              <td>{p.base?.name}</td>
              <td>{p.quantity}</td>
              <td>{new Date(p.date).toLocaleDateString()}</td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default PurchasesPage;
