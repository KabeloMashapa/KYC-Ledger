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