import styled from "styled-components";
import "./LoginPage.css";
import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Body = styled.div`
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 10px;
  text-align: center;
`;

const Input = styled.input`
  font-size: 20px;
  padding: 20px;
  width: 800px;
  border-radius: 10px;
  border: 3px solid #a986ff;
`;

const LoginPage: React.FC = () => {
  const [id, setId] = useState<string>("");
  const [pw, setPw] = useState<string>("");
  const navigate = useNavigate();

  /** 로그인 POST 요청 */
  const handleLogin = () => {
    axios
      .post("http://localhost:8080/api/v1/user/requestLogin", {
        id: id,
        pw: pw
      })
      .then((result) => {
        console.log(result.data);
        navigate("/"); // 로그인 성공 시 홈으로 리다이렉트
      })
      .catch((error) => {
        console.log("회원가입 데이터 전송오류: ", error);
        navigate("/"); // 실패 시 지정된 경로로 리다이렉트(실패페이지 구현 안해서 메인으로)
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
        <button onClick={ handleLogin }>로그인하기</button>
      </Body>
    </div>
  );
};

export default LoginPage;
