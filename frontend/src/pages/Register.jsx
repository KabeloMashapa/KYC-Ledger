import {useState} from "react";
import {Link, useNavigate } from 'react-router-dom';
import {authAPI} from "../services/api.js";
import { toast } from 'react-toastify';

const Register = () => {
    const [form, setForm] = useState({
        fullName:'',email:'',password:'',
        phoneNumber:'',role:'USER'
    });
    const [loading, setLoading] = useState(false);
    const navigate = useNavigate();

    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try {
            await authAPI.register(form);
            toast.success('Registered successfully! Please login. ');
            navigate('/login');
        }
        catch(err) {
            toast.error(err.response?.data?.message || 'Registration Failed');
        }
        finally {
            setLoading(false);
        }
    };
    return (
        <div className= "min-h-screen bg-gray-950 flex items-center justify-center">
            <div className="bg-gray-900 p-8 rounded-2xl shadow-2xl w-full max-w-md border border-gray-800">
                <h2 className="text-3xl font-bold text-white text-center mb-2">
                    KYC Blockchain
                </h2>
                <p className="text-gray-400 text-center mb-8">Create your account</p>
                <form onSubmit={handleSubmit} className="space-y-4">
                    {[
                        {key:'fullName',label:'Full Name',type:'text',placeholder:'UserName'},
                        {key:'email',label:'Email',type:'email',placeholder:'you@example.com'},
                        {key:'phoneNumber',label:'Phone Number',type:'text',placeholder: '+27 123 456 789'},
                        {key:'password',label:'Password',type:'password',placeholder:'Min 8 characters'},
                    ].map((field) => (
                        <div key={field.key}>
                            <label className="text-gray-300 text-sm mb-1 block">{field.label}</label>
                            <input
                                type={field.type}
                                value={form[field.key]}
                                onChange={(e) => setForm({...form, [field.key]: e.target.value})}
                                className="w-full bg-gray-800 text-white rounded-1g px-4 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500 border border-gray-700"
                                placeholder={field.placeholder}
                                required
                                />
                        </div>
                    ))}
                    <div>
                        <label className="text-gray-300 text-sm mb-1 block">Role</label>
                        <select
                            value={form.role}
                            onChange={(e) => setForm({...form,role: e.target.value})}
                            className="w-full bg-gray-800 text-white rounded-1g px py-3 focus:outline focus:ring-blue-500 border border-gray-700">
                            <option value="USER">USER</option>
                            <option value="ADMIN">ADMIN</option>
                            <option value="INSTITUTION">INSTITUTION</option>
                        </select>
                    </div>
                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 rounded-lg transition disabled:opacity-50">
                        {loading ? 'Registering...':'Register'}
                    </button>
                </form>
                <p className="text-gray-400 text-center mt-6">
                    Already have an account?{' '}
                    <Link to="/login" className="text-blue-400 hover:underline">Login</Link>
                </p>
            </div>
        </div>
    );
};
export default Register;