import "./App.css";
import SideBar from "./Pages/SideBar/SideBar";
import MainPage from "./Pages/MainPage";
import LoginPage from "./Pages/Login/LoginPage";
import RegisterPage from "./Pages/Register/RegisterPage";
import WritePage from "./Pages/Write/WritePage";
import PostBoard from "./Pages/PostBoard/PostBoard";
import { Route, Routes } from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';

const App: React.FC = () => {
  return (
    <div className="App">
        <SideBar />
        <Routes>
          <Route path="/" element={<MainPage />} />
          <Route path="/postboard" element={<PostBoard />} />
          <Route path="/login" element={<LoginPage />} />
          <Route path="/register" element={<RegisterPage />} />
          <Route path="/write" element={<WritePage />} />
        </Routes>
    </div>
  );
}

export default App;
