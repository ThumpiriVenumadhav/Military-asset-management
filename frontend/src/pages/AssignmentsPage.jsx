import { useState, useEffect, useContext } from 'react';
import axios from 'axios';
import { AuthContext } from '../context/AuthContext';

function AssignmentsPage() {
  const { token, user } = useContext(AuthContext);

  const [assets, setAssets] = useState([]);
  const [assignments, setAssignments] = useState([]);
  const [form, setForm] = useState({
    assetId: '',
    assignedTo: '',
    quantity: '',
  });
  const [error, setError] = useState('');

  useEffect(() => {
    fetchAssets();
    fetchAssignments();
  }, []);

  const fetchAssets = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/assets', {
        headers: { Authorization: `Bearer ${token}` },
      });
      setAssets(res.data);
    } catch (err) {
      console.error('Error fetching assets:', err.message);
      setError('Could not load assets.');
    }
  };

  const fetchAssignments = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/assignments', {
        headers: { Authorization: `Bearer ${token}` },
      });
      setAssignments(res.data);
    } catch (err) {
      console.error('Error fetching assignments:', err.message);
      setError('Could not load assignments.');
    }
  };

  const getSelectedAssetQuantity = () => {
    const selected = assets.find((a) => a.id === parseInt(form.assetId));
    return selected?.quantity || 0;
  };

  const handleChange = (e) => {
    const { name, value } = e.target;
    setForm({ ...form, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    const available = getSelectedAssetQuantity();
    const requested = parseInt(form.quantity);

    if (requested > available) {
      setError(`Only ${available} units available.`);
      return;
    }

    const payload = {
      asset: { id: parseInt(form.assetId) },
      assignedTo: form.assignedTo,
      quantity: requested,
      assignedOn: new Date().toISOString().slice(0, 10),
    };

    try {
      await axios.post('http://localhost:8080/api/assignments', payload, {
        headers: { Authorization: `Bearer ${token}` },
      });
      setForm({ assetId: '', assignedTo: '', quantity: '' });
      fetchAssignments();
      fetchAssets();
    } catch (err) {
      console.error('Assignment failed:', err.message);
      setError('Assignment failed. Please try again.');
    }
  };

  const handleReturn = async (id) => {
    try {
      await axios.put(`http://localhost:8080/api/assignments/${id}/return`, {}, {
        headers: { Authorization: `Bearer ${token}` },
      });
      fetchAssignments();
      fetchAssets();
    } catch (err) {
      console.error('Return failed:', err.message);
      setError('Failed to mark asset as returned.');
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h2>Assign Assets</h2>

      <form onSubmit={handleSubmit}>
        <label>
          Asset:
          <select name="assetId" value={form.assetId} onChange={handleChange} required>
            <option value="">-- Select Asset --</option>
            {assets.map((a) => (
              <option key={a.id} value={a.id}>
                {a.name} (Stock: {a.quantity})
              </option>
            ))}
          </select>
        </label>

        <label>
          Assigned To:
          <input
            type="text"
            name="assignedTo"
            value={form.assignedTo}
            onChange={handleChange}
            required
          />
        </label>

        <label>
          Quantity (Available: {getSelectedAssetQuantity()}):
          <input
            type="number"
            name="quantity"
            value={form.quantity}
            onChange={handleChange}
            min={1}
            max={getSelectedAssetQuantity()}
            disabled={!form.assetId}
            required
          />
        </label>

        <button type="submit" disabled={!form.assetId}>Assign</button>
        {error && <p style={{ color: 'red' }}>{error}</p>}
      </form>

      <h3>Assignment History</h3>
      <table border="1" cellPadding="5" style={{ borderCollapse: 'collapse' }}>
        <thead>
          <tr>
            <th>Asset</th>
            <th>Assigned To</th>
            <th>Quantity</th>
            <th>Assigned On</th>
            <th>Returned On</th>
            <th>Action</th>
          </tr>
        </thead>
        <tbody>
          {assignments.map((a) => (
            <tr key={a.id} style={{ backgroundColor: a.returnedOn ? '#f0f0f0' : '#ffeeba' }}>
              <td>{a.asset?.name}</td>
              <td>{a.assignedTo}</td>
              <td>{a.quantity}</td>
              <td>{a.assignedOn}</td>
              <td>{a.returnedOn || '-'}</td>
              <td>
                {!a.returnedOn && (
                  <button onClick={() => handleReturn(a.id)}>Mark Returned</button>
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default AssignmentsPage;
