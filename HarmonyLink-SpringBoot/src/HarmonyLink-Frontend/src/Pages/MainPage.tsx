import styled from "styled-components";
import "./MainPage.css";
import * as S from "../style/GlobalStyles"
import axios from 'axios';

const MainPage = () => {

  const checkSession = async () => {
    try {
      const response = await axios.get('http://localhost:8080/api/v1/session/validate', { withCredentials: true });
      console.log('세션 유효: ', response);
      // 세션 유효 시 추가적인 로직 처리
    } catch (error) {
      if (error.response && error.response.status === 401) {
        console.error('세션 만료 또는 유효하지 않음');
        // 세션 만료 시 처리 로직, 예를 들어 로그인 페이지로 리다이렉션
      }
    }
  };    
  
  return (
    <div className="MainPage">
      <S.Body>
        <p>메인입니다</p>
        <button onClick={checkSession}>세션확인</button>
      </S.Body>
    </div>
  );
};

export default MainPage;
