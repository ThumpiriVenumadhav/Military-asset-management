import { useContext } from 'react';
import { AuthContext } from '../context/AuthContext';
import { Link } from 'react-router-dom';

function Navbar() {
  const { userRole, logout } = useContext(AuthContext);

  return (
    <nav style={{ background: '#003f5c', padding: '10px', color: '#fff' }}>
      <Link to="/dashboard" style={{ color: '#fff', marginRight: '10px' }}>Dashboard</Link>

      {userRole === 'ADMIN' && (
        <>
          <Link to="/users" style={{ color: '#fff', marginRight: '10px' }}>Users</Link>
          <Link to="/purchases" style={{ color: '#fff', marginRight: '10px' }}>Purchases</Link>
          <Link to="/Expenditures" style={{color:'#fff',marginRight: '10px'}}>Expenditures</Link>
        </>
      )}

      {userRole === 'COMMANDER' && (
        <Link to="/assignments" style={{ color: '#fff', marginRight: '10px' }}>Assignments</Link>
      )}

      {userRole === 'LOGISTICS' && (
        <Link to="/transfers" style={{ color: '#fff', marginRight: '10px' }}>Transfers</Link>
      )}

      {userRole === 'ADMIN' && (
        <Link to="/assets" style={{ color: '#fff', marginRight: '10px' }}>Assets</Link>
      )}


      <button onClick={logout} style={{ float: 'right', background: '#f04', color: '#fff' }}>
        Logout
      </button>
    </nav>
  );
}

export default Navbar;
