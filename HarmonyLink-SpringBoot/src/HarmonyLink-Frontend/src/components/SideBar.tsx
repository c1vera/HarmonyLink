import { Link } from "react-router-dom";
import styled from "styled-components";
import "./SideBar.css";
import UserView from "./UserView";

const Body = styled.div`
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 10px;
  text-align: center;
`;

const Logo = styled.div`
  font-size: 40px;
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
`;

const Content = styled.div`
  align-items: center;
  flex: 5;
  display: flex;
  justify-content: center;
  align-items: center;
`;

function SideBar() {
  return (
    <div className="SideBar">
      <Body>
        <Logo>
          <Link to="/">
            <h3>HarmonyLink</h3>
          </Link>
        </Logo>
        <Content>본문</Content>
        <UserView/>
      </Body>
    </div>
  );
}

export default SideBar;
