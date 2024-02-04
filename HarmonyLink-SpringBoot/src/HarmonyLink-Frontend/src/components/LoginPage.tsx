import styled from "styled-components";
import "./LoginPage.css";
import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useDispatch } from "react-redux";
import { loginUser } from "../features/auth/authSlice";

const Body = styled.div`
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
`;

const Input = styled.input`
  font-size: 20px;
  padding: 20px;
  width: 800px;
  border-radius: 10px;
  border: 3px solid #a986ff;
`;

const LoginMessage = styled.p`
  color: red;
  padding: 10px;
`

const LoginPage: React.FC = () => {
  const [id, setId] = useState<string>("");
  const [pw, setPw] = useState<string>("");
  const [loginMessage, setLoginMessage] = useState<string>("");
  const navigate = useNavigate();
  const dispatch = useDispatch();

  /** 로그인 POST 요청 */
  const handleLogin = () => {
    axios
      .post("http://localhost:8080/api/v1/user/requestLogin", { id, pw })
      .then((result) => {
        dispatch(loginUser(result.data.user));
        // 로그인 성공 처리
        console.log(result.data);
        navigate("/"); // 홈으로 리다이렉트
      })
      .catch((error) => {
        if (error.response) {
          // 서버가 2xx 이외의 상태 코드로 응답한 경우
          console.error("에러 상태 코드: ", error.response.status);
          console.error("에러 메시지: ", error.response.data.message);

          if (error.response.status === 401) {
            // 아이디나 비밀번호가 잘못된 경우의 처리
            setLoginMessage("아이디 또는 비밀번호가 잘못되었습니다.");
          } else if (error.response.status >= 500) {
            // 서버 오류의 처리
            setLoginMessage("서버 오류가 발생했습니다. 나중에 다시 시도해주세요.");
          } else {
            // 기타 상황의 처리
            setLoginMessage("로그인을 할 수 없습니다. 다시 시도해주세요.");
          }
        } else if (error.request) {
          // 요청이 이루어졌으나 응답을 받지 못한 경우
          console.error("서버에서 응답이 없습니다.");
          setLoginMessage("서버 응답이 없습니다. 네트워크 연결을 확인해주세요.");
        } else {
          // 요청 설정 중 발생한 오류 처리
          console.error("요청 오류: ", error.message);
        }
      });
  };

  

  return (
    <div className="MainPage">
      <Body>
        <h3>로그인</h3>
        <p>아이디</p>
        <Input
          type="text"
          onChange={(e) => {
            setId(e.target.value);
          }}
        ></Input>
        <p>비밀번호</p>
        <Input
          type="password"
          onChange={(e) => {
            setPw(e.target.value);
          }}
        ></Input>
        <LoginMessage>{loginMessage}</LoginMessage>
        <button onClick={handleLogin}>로그인하기</button>
      </Body>
    </div>
  );
};

export default LoginPage;
