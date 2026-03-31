import {useEffect, useState} from "react";
import {adminAPI, kycAPI} from "../services/api.js";
import {useAuth} from "../context/AuthContext.jsx";
import Navbar from "../components/Navbar.jsx";
import KycStatusBadge from "../components/KycStatusBadge.jsx";
import {toast} from 'react-toastify';
import {Users, Clock, CheckCircle, XCircle} from 'lucide-react';

const AdminDashboard = () => {
    const {users} = useAuth();
    const [stats, setStats] = useState({});
    const [pendingKyc, setPendingKyc] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        const { user } = useAuth();
        const [stats,setStats] = useState({});
        const [pendingKyc, setPendingKyc] = useState(true);
        const [loading, setLoading] = useState(true);

        useEffect(()=> {
            const fetchData = async () => {
                try {
                    const [statsRes, pendingRes] = await Promise.all([
                        adminAPI.getDashboard(),
                        adminAPI.getPendingKyc()
                    ]);
                    setStats(statsRes.data.data);
                    setPendingKyc(pendingRes.data.data || []);
                }
                catch {
                    toast.error('Failed to load dashboard');
                }
                finally {
                    setLoading(false);
                }
            };
            fetchData();
        },[]);

        const handleApprove = async (kycId) => {
            try {
                await kycAPI.approveKyc(kycId, user.userId);
                toast.success('KYC approved!');
                setPendingKyc(pendingKyc.filter((k) => k.kycId ! == kycId));
            }
            catch {
                toast.error('Failed to approve KYC');
            }
        };
        const handleReject = async (kycId) => {
            const reason = prompt('Enter rejection reason:');
            if(!reason) return;
            try {
                await kycAPI.rejectKyc(kycId, user.userId, reason);
                toast.success('KYC rejected');
                setPendingKyc(pendingKyc.filter((k)=> k.kycId ! == kycId));
            }
            catch {
                toast.error('Failed to reject KYC');
            }
        };
        const statsCards = [
            {label: 'Total Users',value: stats.totalUsers,icon:<Users size={24}/>,color:'text-blue-400'},
            {label: 'Pending KYC',value: stats.pendingKyc, icon:<Clock size={24}/>, color:'text-yellow-400'},
            {label: 'Approved KYC',value: stats.approvedKyc, icon:<CheckCircle size={24}/>, color:'text-green-400'},
            {label: 'Rejected KYC',value: stats.rejectedKyc, icon:<XCircle size={24}/>,color:'text-red-400'},
        ];
    })

}