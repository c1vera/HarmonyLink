import styled from "styled-components";
import { useParams } from "react-router-dom";
import { useLocation } from "react-router-dom";

const Top = styled.div`
  text-align: left;
  padding: 20px;
  flex: 1;
`;

const Body = styled.div`
  height: 100%;
  display: flex;
  flex-direction: column;
  padding: 40px;
`;

const Content = styled.div`
  text-align: center;
  flex: 6;
`;
const Footer = styled.div`
  text-align: center;
  flex: 1;
`;

const PostDetailPage = () => {
  const location = useLocation();
  const { post } = location.state || {}; // 넘겨받은 state에서 post 추출

  return (
    <div className="MainPage">
      <Body>
        <Top>
          <h3>{post?.type} 게시판</h3>
          <h4>{post?.music_key}</h4>
        </Top>
        <Content>
          <h3>{post?.title}</h3>
          <br></br>
          <p>{post?.content}</p>
        </Content>
        <Footer>
          <p>💜 {post?.thumbsUp}</p>
        </Footer>
      </Body>
    </div>
  );
};

export default PostDetailPage;
