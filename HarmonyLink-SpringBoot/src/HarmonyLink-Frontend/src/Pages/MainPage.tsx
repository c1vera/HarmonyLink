import styled from "styled-components";
import "./MainPage.css";

const Body = styled.div`
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  padding: 40px;
`;

const MainPage: React.FC = () => {
  return (
    <div className="MainPage">
      <Body>
        <p>메인입니다</p>
      </Body>
    </div>
  );
};

export default MainPage;
