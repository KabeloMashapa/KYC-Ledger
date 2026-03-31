import {useState} from "react";
import {useNavigate } from 'react-router-dom';
import {useAuth} from "../context/AuthContext.jsx";
import {kycAPI} from "../services/api.js";
import Navbar from "../components/Navbar.jsx";
import {toast} from 'react-toastify';

const SubmitKyc = () => {
    const { user } = useAuth();
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const [form, setForm] = useState({
        firstName: '',lastName:'',dateOfBirth:'',
        nationality:'',idNumber:'',address:'',
        city:'',country:''
    });
    const handleSubmit = async (e) => {
        e.preventDefault();
        setLoading(true);
        try{
            const res = await kycAPI.submitKyc({...form, userId:user.userId});
            toast.success('KYC submitted successfully!');
            navigate(`/kyc/${res.data.data.kycId}`);
        }
        catch(err) {
            toast.error(err.response?.data?.message || 'Submission failed');
        }
        finally {
            setLoading(false);
        }
    };
    const fields = [
        {name:'firstName',label:'First Name',type:'text',placeholder:'John'},
        {name:'lastName',label:'Last Name',type:'text',placeholder: 'Doe'},
        {name:'dateOfBirth',label:'Date of Birth',type:'date',placeholder: ''},
        {name:'nationality',label:'Nationality',type:'text',placeholder:'South African'},
        {name:'idNumber',label:'ID / Password Number',type:'text',placeholder: '1234567890'},
        {name:'address',label:'Address',type:'text',placeholder: '123 Main St'},
        {name:'city',label:'City',type:'text',placeholder: 'Pretoria'},
        {name:'country',label:'Country',type:'text',placeholder: 'South Africa'},
    ];
    return (
        <div className="min-h-screen bg-gray-950 text-white">
            <Navbar/>
            <div className="max-w-2xl mx-auto px-6 py-10">
                <h1 className="text-3xl font-bold mb-2">Submit KYC</h1>
                <p className="text-gray-400 mb-8">Fill in your personal details below</p>
                <form onSubmit={handleSubmit}
                      className="bg-gray-900 rounded-2xl p-8 space-y-5 border border-gray-800">
                    <div className="grid grid-cols-2 gap-5">
                        {fields.map((field) => (
                            <div key={field.name}
                            className={field.name === 'address'?'col-span-2':''}>
                                <label className="text-gray-300 text-sm mb-1 block">
                                    {field.label}
                                </label>
                                <input
                                    type={field.type}
                                    name={field.name}
                                    value={form[field.name]}
                                    onChange={(e) => setForm({...form, [field.name]: e.target.value})}
                                    placeholder={field.placeholder}
                                    required
                                    className="w-full bg-gray-800 text-white rounded-lg px-4 py-3 focus:outline-none focus:ring-2 focus:ring-blue-500 border border-gray-700"
                                    />
                            </div>
                        ))}
                    </div>
                    <button
                        type="submit"
                        disabled={loading}
                        className="w-full bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 rounded-lg transition disabled:opacity-50">
                        {loading ? 'Submitting...' : 'Submit KYC'}
                    </button>
                </form>

            </div>
        </div>
    );
};
export default SubmitKyc;