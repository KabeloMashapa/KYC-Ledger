import { useState} from "react";
import {Link, useNavigate} from "react-router-dom";
import {useAuth} from "../context/AuthContext.jsx";
import {authAPI} from "../services/api.js";
import {toast} from 'react-toastify';

const Login = () => {
    const [form, setForm] = useState({email:'',password:''});
    const [loading,setLoading] = useState(false);
    const {login} = useAuth();
    const navigate = useNavigate();


    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            const res = await authAPI.login(form);
            const {token, ...userData } = res.data.data;
            login(userData, token);
            toast.success('Login successful!');
            navigate('/dashboard');
        }
        catch(err) {
            toast.error(err.response?.message || 'Login Failed');
        }
        finally {
            setLoading(false);
        }
    };
    return (
        <div className="min-h-screen bg-gray-950 flex items-center justify-center">
            <div className="bg-gray-900 p-8 rounded-2xl shadow-2xl w-full max-w-md border border-gray-800">
                <h2 className="text-3xl font-bold text-white text-center mb-2">
                     KYC Blockchain
                </h2>
                <p className="text-gray-400 text-center mb-8">Sign in to your account</p>

                <form onSubmit={handleSubmit} className="space-y-5">
                    <div>
                        <label className="text-gray-300 text-sm mb-1 block">Email</label>
                        <input
                            type="email"
                            value={form.email}
                            onChange={(e) => setForm({ ...form, email: e.target.value })}
                            className="w-full bg-gray-800 text-white rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500 border border-gray-700"
                            placeholder="you@example.com"
                            required
                        />
                    </div>
                    <div>
                        <label className="text-gray-300 text-sm mb-1 block">Password</label>
                        <input
                            type="password"
                            value={form.password}
                            onChange={(e) => setForm({ ...form, password: e.target.value })}
                            className="w-full bg-gray-800 text-white rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500 border border-gray-700"
                            placeholder="••••••••"
                            required
                        />
                    </div>
                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 rounded-lg transition disabled:opacity-50">
                        {loading ? 'Signing in...' : 'Sign In'}
                    </button>
                </form>

                <p className="text-gray-400 text-center mt-6">
                    Don't have an account?{' '}
                    <Link to="/register" className="text-blue-400 hover:underline">
                        Register
                    </Link>
                </p>
            </div>
        </div>
    );
};
export default Login;