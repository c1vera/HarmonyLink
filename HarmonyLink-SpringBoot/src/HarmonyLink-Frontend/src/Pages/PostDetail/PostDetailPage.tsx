import styled from "styled-components";
import { useParams } from "react-router-dom";
import { useLocation } from "react-router-dom";
import * as S from "../../style/GlobalStyles"
import Input from "../../components/Input";
import Button from "../../components/Button";
import axios from "axios";

import { useState } from "react";

const Top = styled.div`
display: flex;
width: 100%;
`
const Type = styled.div`
display: flex;
width: 50%;
justify-content: left;
`
const Info = styled.div`
display: flex;
width: 50%;
justify-content: right;
> * {
  padding: 0px 10px;
}
`

const Title = styled.div`
width: 100%;
height: 50px;
display: flex;
  justify-content: center;
  align-items: center;
  padding: 0px;
`;

const Content = styled.div`
  text-align: center;
  min-height: 300px;
`;
const Footer = styled.div`
display: flex;
  justify-content: center;
  align-items: center;
  height: 50px;
  padding: 70px;
`;

const CommentArea = styled.div`
  flex: 2;
  > * {
  margin: 10px;
}
`

const TrackArea = styled.div`
display: flex;
height: 200px;
padding: 20px;
justify-content: center;
align-items: center;
text-align:center;
> * {
  padding: 10px;
}
`

const TrackImg = styled.img`
  width: 150px;
  height: 150px;
`

const PostDetailPage = () => {
  const location = useLocation();
  const { post } = location.state || {}; // 넘겨받은 state에서 post 추출
  const [comment, setComment] = useState<string>("");
  const handleThumbsUp = () => {
    
  }

  const haldlePostComment = async () => {
    try {
      const result = await axios.post(
        "http://localhost:8080/api/v1/user/requestPost",
        {
          comment: comment
        },
        { withCredentials: true }
      );
      console.log(result.data);
    } catch (error) {
      console.log("댓글쓰기 오류: ", error);
    }
  }
  return (
    <div className="MainPage">
      <S.ScrollBody>
        <Top>
        <Type>
        <h6>{post?.type} 게시판</h6>
        </Type>
        <Info>
          <h6>작성자 : {post?.nickname}</h6>
          <h6>조회수 : {post?.view}</h6>
        </Info>
        </Top>
              
        <Title>
        <h3>{post?.title}</h3>
        </Title>
        <TrackArea>
          <TrackImg src={post?.imgUri}/>
          <p>{post?.artistName}<br/>{post?.trackName}</p>
          </TrackArea>
        <Content>
          <p>{post?.content}</p>          
        </Content>
        <Footer>
          <p>💜 {post?.thumbsUp}</p>
        </Footer>
        <CommentArea>
          <h3>댓글</h3>
          <p>댓글1</p>
          <p>댓글2</p>
          <p>댓글3</p>
          <p>
        <Input width={"400px"}
        onChange={(e) => {
          setComment(e.target.value);
        }} />
        <Button theme={"default"} size={"small"} onClick={haldlePostComment}>작성하기</Button>
        </p>
          
        </CommentArea>
      </S.ScrollBody>
    </div>
  );
};

export default PostDetailPage;
