import "./App.css";
import SideBar from "./Pages/SideBar/SideBar";
import MainPage from "./Pages/MainPage";
import LoginPage from "./Pages/Login/LoginPage";
import RegisterPage from "./Pages/Register/RegisterPage";
import WritePage from "./Pages/Write/WritePage";
import PostBoard from "./Pages/PostBoard/PostBoard";
import { Route, Routes } from "react-router-dom";
import "bootstrap/dist/css/bootstrap.min.css";
import PrivateRoute from "./PrivateRoute";
import Test from "./Pages/Spotify/Test"
import PostDetailPage from "./Pages/PostDetail/PostDetailPage";
import Search from "./Pages/Spotify/Search";
import axios from "axios";
import {  useDispatch } from "react-redux";
import {  AppDispatch, persistor } from "./redux/store";
import { logoutUser } from "./redux/actions/userActions";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";
const App = () => {
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();

  useEffect(() => {
    const interceptor = axios.interceptors.response.use(
      response => response,
      error => {
        console.error("Logout failed:", error);
        if (error.response && error.response.status === 401) {
          console.error('Session expired. User needs to login again.');
          dispatch(logoutUser());
          sessionStorage.removeItem('user');
          document.cookie = 'session_id=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
          persistor.purge().then(() => {
            console.log('Session expired. Persisted state has been removed.');
          });
          navigate("/login");
        }
        return Promise.reject(error);
      }
    );
    return () => {
      axios.interceptors.response.eject(interceptor);
    };
  }, [dispatch, navigate]);
  
  return (
    <div className="App">
      <SideBar />
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/postboard" element={<PostBoard key={Date.now()}/>} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/test" element={<Test />} />
        <Route
          path="/write"
          element={
            <PrivateRoute>
              <WritePage />
            </PrivateRoute>
          }
        />
        <Route path="/detail/:id" element={<PostDetailPage />} />
        <Route path="/search" element={<Search />} />
      </Routes>
    </div>
  );
};

export default App;
