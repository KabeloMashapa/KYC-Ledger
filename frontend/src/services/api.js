import axios from 'axios';
import config from "tailwindcss/defaultConfig.d.ts";
const API_BASE_URL = 'http://localhost:8080/api';
const api = axios.create({
    baseURL: API_BASE_URL,
    headers:{
        'Content-Type':'application/json',
    },
});
// Attach JWT token to every request
api.interceptors.request.use((config) => {
    const token = localStorage.getItem('token');
    if(token) {
        config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
});
// Handle 401 unauthorised globally
api.interceptors.response.use(
    (response) => response,
    (error) => {
        if(error.response?.status === 401) {
            localStorage.clear();
            window.location.href = '/login'
        }
        return Promise.reject(error);
    }
);
export const authAPI = {
    register: (data) => api.post('/auth/register/',data),
    login: (data) => api.post('/auth/login',data),
};
export const kycAPI = {
    submitKyc: (data) => api.post('/api/kyc/submit',data),
    getKycById: (kycId) => api.get(`/kyc/${kycId}`),
    getKycByUserId: (userId) => api.get(`/kyc/user/${userId}`),
    verifyKyc: (kycId)=> api.get(`/kyc/${kycId}/verify`),
    approveKyc:(kycId,adminId) => api.put(`/kyc/${kycId}/approve?adminId=${adminId}`),
    rejectKyc:(kycId,adminId,reason) => api.put(
        `/kyc/${kycId}/reject?adminId=${adminId}&reason=${reason}`
    ),
};
export const docuemntAPI = {
    uploadDocument: (kycAPI,formData) => api.post(`/documents/upload/${kycAPI}`,formData,{
        headers: {'Content-Type':'multipart/form-data'},
    }),
    getDocuments: (kycId) => api.get(`/documents/${kycId}`),

}