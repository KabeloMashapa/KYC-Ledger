import {Link, useNavigate } from 'react-router-dom';
import {useAuth} from "../context/AuthContext.jsx";
import {LogOut, User, Shield} from 'lucide-react';

const Navbar = () => {
    const {user, logout, isAdmin} = useAuth();
    const navigate = useNavigate();

    const handleLogout = () => {
        logout();
        navigate('/login');
    };
    return (
        <nav className="bg-gray-900 text-white px-6 py-4 flex justify-between items-center shadow-lg border-b border-gray-800">
            <Link to="/dashboard" className="text-xl font-bold text-blue-400">
                KYC BLOCKCHAIN
            </Link>
            <div className="flex items-center gap-6">
                {isAdmin() && (
                    <Link to="/admin"
                          className="flex items-center gap-1 text-gray-300 hover:text-blue-400 transition">
                           <Shield size={16}/> Admin
                    </Link>
                )}
                <span className="flex items-center gap-1 text-gray-300">
                    <User size = {16}/> {user?.fullName}
                </span>
                <button onClick={handleLogout}
                        className="flex items-center gap-1 text-gray-300 hover:text-red-400 transition">
                        <LogOut size = {16}/>
                </button>
            </div>
        </nav>
    );
};
export default Navbar;