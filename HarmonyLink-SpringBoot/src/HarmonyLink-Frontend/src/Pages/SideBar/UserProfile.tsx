import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { AppState, AppDispatch } from "../../redux/store";
import { logoutUser } from "../../redux/actions/userActions";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const UserProfile: React.FC = () => {
  const userInfo = useSelector((state: AppState) => state.user.userInfo);
  const dispatch = useDispatch<AppDispatch>();
  const navigate = useNavigate();

  /** 로그아웃 POST 요청 */
  const handleLogout = () => {
    axios
      .post(
        "http://localhost:8080/api/v1/user/requestLogout",
        {},
        {
          withCredentials: true, // 쿠키 정보를 함께 보내기 위해 필요
        }
      )
      .then((response) => {
        // 로그아웃 성공 처리
        dispatch(logoutUser());
        navigate("/");
      })
      .catch((error) => {
        // 에러 처리
        console.error("Logout failed:", error);
      });
  };

  if (!userInfo) {
    return (
      <>
        <Link to="/login">로그인</Link>
        <Link to="/register">회원가입</Link>
      </>
    );
  }

  return (
    <div>
      
      <br />
      {userInfo.nickname}님, 반갑습니다!{" "}
      <button onClick={handleLogout}>로그아웃</button>
    </div>
  );
};

export default UserProfile;
