import styled from "styled-components";
import "./MainPage.css";
import * as S from "../style/GlobalStyles"

const MainPage: React.FC = () => {
  return (
    <div className="MainPage">
      <S.Body>
        <p>메인입니다</p>
      </S.Body>
    </div>
  );
};

export default MainPage;
