import {createContext, useContext, useState, useEffect} from 'react';
const AuthContext = createContext(null);

export const AuthProvider = ({children}) => {
    const [user, setUser] = useState(null);
    const [token, setToken] = useState(null);
    const [loading,setLoading] = useState(true);

    useEffect(() => {
        const savedToken = localStorage.getItem('token');
        const savedUser = localStorage.getItem('user');
        if(savedToken && savedUser) {
            setToken(savedToken);
            setUser(JSON.parse(savedUser));
        }
        setLoading(false);
    },[]);
    const login = (userData, jwtToken) => {
        setUser(userData);
        setToken(jwtToken);
        localStorage.setItem('token',jwtToken);
        localStorage.setItem('user',JSON.stringify(userData));
    };
    const logout = () => {
        setUser(null);
        setToken(null);
        localStorage.clear();
    };
    const isAdmin = () => user?.role === 'ADMIN';
    const isUser = () => user?.role === 'USER';
    const isInstitution = () => user?.role === 'INSTITUTION';

    return (
        <AuthContext.Provider value={{
            user,token,loading,
            login,logout,
            isAdmin,isUser,isInstitution
        }}>
            {children}
        </AuthContext.Provider>
    );
};
export const useAuth = () => useContext(AuthContext);