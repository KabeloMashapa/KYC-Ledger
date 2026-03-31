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
    })

}