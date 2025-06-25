import { useEffect, useState, useContext } from 'react';
import { getDashboardData } from '../services/dashboardService';
import { AuthContext } from '../context/AuthContext';
import Navbar from '../components/Navbar';

function DashboardPage() {
  const { token, userRole } = useContext(AuthContext);
  const [dashboard, setDashboard] = useState(null);
  const [error, setError] = useState('');

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getDashboardData(token);
        setDashboard(data);
      } catch (err) {
        console.error('Dashboard fetch failed:', err.message);
        setError('Unable to load dashboard data. Please try again later.');
      }
    };

    if (token) {
      fetchData();
    }
  }, [token]);

  if (error) {
    return (
      <div>
        <Navbar />
        <h2>Error</h2>
        <p style={{ color: 'red' }}>{error}</p>
      </div>
    );
  }

  if (!dashboard) {
    return (
      <div>
        <Navbar />
        <p>Loading dashboard...</p>
      </div>
    );
  }

  return (
    <div>
      <Navbar />
      <h2 style={{ margin: '20px' }}>{userRole} Dashboard</h2>

      <ul style={{ listStyle: 'none', padding: 0, margin: '20px' }}>
        {dashboard.totalUsers !== undefined && <li>Total Users: {dashboard.totalUsers}</li>}
        {dashboard.totalAssets !== undefined && <li>Total Assets: {dashboard.totalAssets}</li>}
        {dashboard.totalAssignments !== undefined && <li>Total Assignments: {dashboard.totalAssignments}</li>}
        {dashboard.totalTransfers !== undefined && <li>Total Transfers: {dashboard.totalTransfers}</li>}
        {dashboard.totalPurchases !== undefined && <li>Total Purchases: {dashboard.totalPurchases}</li>}
      </ul>
    </div>
  );
}

export default DashboardPage;
