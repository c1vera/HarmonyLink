import { Link } from "react-router-dom";
import styled from "styled-components";
import "./UserView.css";

const Login = styled.div`
  flex: 1;
  font-size: 25px;
  margin: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px 0px;
`;

function UserView() {
  return (
    <div className="UserView">
      <Link to="/write">글쓰기</Link>
      <Login>      
      
        <Link to="/login">로그인</Link>
        <Link to="/register">회원가입</Link>
      </Login>
    </div>
  );
}

export default UserView;