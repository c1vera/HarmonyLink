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
        if (error.response && error.response.status === 401) {
          // 401 에러 처리 로직
          console.error('Session expired. User needs to login again.');
      
          // 로그아웃 상태로 전환
          dispatch(logoutUser());
      
          // 세션 스토리지에서 사용자 정보 제거
          sessionStorage.removeItem('user');
      
          // 쿠키 삭제
          document.cookie = 'session_id=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;';
      
          // persistor 상태 제거
          persistor.purge().then(() => {
            console.log('Session expired. Persisted state has been removed.');
          });
      
          // 사용자를 로그인 페이지로 리디렉트
          navigate("/login");
        }
      
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
        <Button size={'small'} theme={'default'} onClick={handleLogout}>회원가입</Button>
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
