import { BrowserRouter, Routes, Route } from 'react-router-dom';
import LoginPage from './pages/LoginPage';
import DashboardPage from './pages/DashboardPage';
import UsersPage from './pages/UsersPage';
import PurchasesPage from './pages/PurchasesPage';
import TransfersPage from './pages/TransfersPage';
import PrivateRoute from './components/PrivateRoute';
import AssignmentsPage from './pages/AssignmentsPage';
import ExpendituresPage from './pages/ExpendituresPage';
import AssetPage from './pages/AssetPage';


function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LoginPage />} />

        <Route path="/dashboard" element={
          <PrivateRoute>
            <DashboardPage />
          </PrivateRoute>
        } />

        <Route path="/users" element={
          <PrivateRoute>
            <UsersPage />
          </PrivateRoute>
        } />

        <Route path="/purchases" element={
          <PrivateRoute>
            <PurchasesPage />
          </PrivateRoute>
        } />

        <Route path="/transfers" element={
          <PrivateRoute>
            <TransfersPage />
          </PrivateRoute>
        } />

      <Route path="/expenditures" element={
        <PrivateRoute>
          <ExpendituresPage />
        </PrivateRoute>
        } />

      <Route path="/assignments" element={
        <PrivateRoute>
          <AssignmentsPage />
        </PrivateRoute>
      } /> 


      <Route path="/assets" element={
        <PrivateRoute>
          <AssetPage />
        </PrivateRoute>
      } />

        <Route path="/expenditures" element={
        <PrivateRoute>
          <ExpendituresPage />
        </PrivateRoute>
      } />

      </Routes>
    </BrowserRouter>
  );
}

export default App;
