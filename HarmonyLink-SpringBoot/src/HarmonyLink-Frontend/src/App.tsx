import "./App.css";
import SideBar from "./components/SideBar";
import MainPage from "./components/MainPage";
import LoginPage from "./components/LoginPage";
import RegisterPage from "./components/RegisterPage";
import { Route, Routes } from "react-router-dom";
import { useState } from "react";
const App: React.FC = () => {
  const [loginstate, setLoginstate] = useState<boolean>(false);

  return (
    <div className="App">
        <SideBar />
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
        </Routes>
    </div>
  );
}

export default App;
