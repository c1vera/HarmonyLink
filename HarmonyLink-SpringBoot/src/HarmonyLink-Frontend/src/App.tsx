import "./App.css";
import SideBar from "./Pages/SideBar/SideBar";
import MainPage from "./Pages/MainPage";
import LoginPage from "./Pages/Login/LoginPage";
import RegisterPage from "./Pages/Register/RegisterPage";
import WritePage from "./Pages/Write/WritePage";
import { Route, Routes } from "react-router-dom";

const App: React.FC = () => {
  return (
    <div className="App">
        <SideBar />
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/write" element={<WritePage />} />
        </Routes>
    </div>
  );
}

export default App;
