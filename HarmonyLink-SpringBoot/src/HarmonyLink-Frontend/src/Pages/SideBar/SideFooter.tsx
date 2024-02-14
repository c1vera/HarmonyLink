import { Link } from "react-router-dom";
import styled from "styled-components";
import UserProfile from "./UserProfile";

const Login = styled.div`
  flex: 1;
  font-size: 25px;
  margin: 10px;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 20px 0px;
`;

function SideFooter() {
  return (
    <div className="UserView">
      <Link to="/write">
        <span style={{ fontSize: "40px" }}>글쓰기</span>
      </Link>
      <Login>      
        <UserProfile/>
      </Login>
    </div>
  );
}

export default SideFooter;
