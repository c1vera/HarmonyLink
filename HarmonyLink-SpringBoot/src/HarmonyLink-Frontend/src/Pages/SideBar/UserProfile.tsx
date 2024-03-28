import { Link } from "react-router-dom";
import { useSelector, useDispatch } from "react-redux";
import { AppState, AppDispatch, persistor } from "../../redux/store";
import { logoutUser } from "../../redux/actions/userActions";
import { useNavigate } from "react-router-dom";
import axios from "axios";
import Button from "../../components/Button";
const UserProfile = () => {
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
        persistor.purge().then(() => {
          console.log('Logout successful. Persisted state has been removed.');
        });
      })
      .catch((error) => {
        // 에러 처리
        console.error("Logout failed:", error);
      
        // 오류를 다음으로 전달
        return Promise.reject(error);
      });
          
  };

  if (!userInfo) {
    return (
      <>
        <Link to="/login">
        <Button size={'small'} theme={'default'} >로그인</Button>
        </Link>
        <Link to="/register">
        <Button size={'small'} theme={'default'} >회원가입</Button>
        </Link>
      </>
    );
  }

  return (
    <div>
      
      
      {userInfo.nickname}님, 반갑습니다!{" "}
      <br />
      <Button size={'small'} theme={'default'} onClick={handleLogout}>로그아웃</Button>
    </div>
  );
};

export default UserProfile;
