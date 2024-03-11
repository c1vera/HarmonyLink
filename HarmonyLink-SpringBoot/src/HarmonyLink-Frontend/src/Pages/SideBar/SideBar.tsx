import { Link } from "react-router-dom";
import styled from "styled-components";
import "./SideBar.css";
import UserView from "./SideFooter";
import Title from "../../components/Text";

const Body = styled.div`
  height: 100%;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  text-align: center;
`;

const Logo = styled.div`
  flex: 1;
  display: flex;
  justify-content: center;
  align-items: center;
  text-shadow: 5px 5px 4px rgba(154, 154, 154, 0.559);
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
            <Title text={"HarmonyLink"} color={"#a986ff"}/>
          </Link>
        </Logo>
        <Content><Link to="/postboard">
            <h3>글목록</h3>
          </Link></Content>
        <UserView/>
      </Body>
    </div>
  );
}

export default SideBar;
