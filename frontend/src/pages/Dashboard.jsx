import {useEffect,useState} from "react";
import {Link} from 'react-router-dom';
import {useAuth} from "../context/AuthContext.jsx";
import {kycAPI} from "../services/api.js";
import Navbar from "../components/Navbar.jsx";
import KycStatusBadge from "../components/KycStatusBadge.jsx";
import {toast} from 'react-toastify';
import {PlusCircle, Eye} from 'lucide-react';

const Dashboard= () => {
    const { user } = useAuth();
    const [kycRecords, setKycRecords] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const fetchKyc = async () => {
            try {
                const res = await kycAPI.getKycByUserId(user.userId);
                setKycRecords(res.data.data || []);
            }
            catch {
                toast.error('Failed to load KYC records');
            }
            finally{
                setLoading(false);
            }
        };
        fetchKyc();
    },[user]);

    return (
        <div className="min-h-screen bg-gray-950 text-white">
            <Navbar/>
            <div className="max-w-5xl mx-auto px-6 py-10">
                <div className="flex justify-between items-center mb-8">
                    <div>
                        <h1 className="text-3xl font-bold">Welcome, {user?.fullName}</h1>
                        <p className="text-gray-400 mt-1">Manage your KYC records</p>
                    </div>
                    <Link to="/submit-kyc"
                          className="flex items-center gap-2 bg-blue-600 hover:bg-blue-700 px-5 py-3 rounded-lg font-semibold transition">
                        <PlusCircle size={18}/>Submit KYC

                    </Link>
                </div>
                {loading ? (
                    <div className="text-center text-gray-400 py-20">Loading...</div>
                ): kycRecords.length === 0 ? (
                    <div className="text-center py-20 bg-gray-900 rounded-2xl border border-gray-800">
                        <p className="text-gray-400 text-lg mb-4">No KYC records found</p>
                        <Link to="/submit-kyc"
                              className="bg-blue-600 hover:bg-blue-700 px-6 py-3 rounded-lg transition">
                            Submit your first KYC

                        </Link>
                    </div>

                ) : (
                    <div className="space-y-4">
                        {kycRecords.map((kyc) => (
                            <div key={kyc.id}
                            className="bg-gray-900 rounded-2xl p-6 flex justify-between items-center border border-gray-800">
                                <div>
                                    <p className="font-semibold text-lg">{kyc.fullName}</p>
                                    <p className="text-gray-400 text-sm mt-1">KYC ID: {kyc.kycId}</p>
                                    <p className="text-gray-400 text-sm">
                                        Submitted: {new Date(kyc.submittedAt).toLocaleDateString()}
                                    </p>
                                </div>
                                <div className="flex items-center gap-4">
                                    <KycStatusBadge status={kyc.status}/>
                                    <Link to={`/kyc/${kyc.kycId}`}
                                          className="flex items-center gap-1 text-blue-400 hover:underline">
                                        <Eye size={16}/> View
                                    </Link>
                                </div>
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
};
export default Dashboard;