import {BrowserRouter, Routes, Route, Navigate} from 'react-router-dom';
import {ToastContainer} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {AuthProvider} from "./context/AuthContext.jsx";
import ProtectedRoute from './components/ProtectedRoute';
import Login from './pages/Login.jsx'
import Register from "./pages/Register.jsx";
import DashBoard from './pages/Dashboard.jsx';
import SubmitKyc from "./pages/SubmitKyc.jsx";
import AdminDashboard from "./pages/AdminDashboard.jsx";

function App() {
    return (
        <AuthProvider>
            <BrowserRouter>
                <Routes>
                    <Route path="/login" element={<Login />} />
                    <Route path="/register" element={<Register />} />
                    <Route path="/dashboard" element={
                        <ProtectedRoute><DashBoard/></ProtectedRoute>
                    }/>
                    <Route path="/submitKyc" element={
                        <ProtectedRoute>SubmitKyc</ProtectedRoute>
                    }/>
                    <Route path="/admin" element={
                        <ProtectedRoute requiredRole="ADMIN">
                            <AdminDashboard/>
                        </ProtectedRoute>
                    }/>
                    <Route path="/" element={<Navigate to="/dashboard" replace /> } />
                </Routes>
            </BrowserRouter>
            <ToastContainer position = "top-right" theme="dark"/>
        </AuthProvider>
    );
}
export default App;