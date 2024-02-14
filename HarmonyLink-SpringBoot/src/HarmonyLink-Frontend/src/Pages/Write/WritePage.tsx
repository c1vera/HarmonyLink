import styled from "styled-components";
import Input from "../../components/Input/Input";
import TextArea from "../../components/Textarea/Textarea";
import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { AppState } from "../../redux/store";

const Body = styled.div`
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  margin:40px;
`;

const TitleMusicArea = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  align-items: center; /* 또는 flex-start, flex-end 사용 */
`;

const MbtiArea = styled.div`
  display: flex;
  flex-grow: 0;
  align-items: center; /* 또는 flex-start, flex-end 사용 */
  > * {
    /* 직계 자식 요소만 선택 */
    margin: 0px 30px; /* 예시 마진 값 */
  }
`;
const MusicArea = styled.div`
  display: flex;
  flex-grow: 0;
  align-items: center; /* 또는 flex-start, flex-end 사용 */
  > * {
    /* 직계 자식 요소만 선택 */
    margin: 0px 30px; /* 예시 마진 값 */
  }
`;

const WritePage: React.FC = () => {
  const [title, setTitle] = useState<string>("");
  const [content, setContent] = useState<string>("");
  const [postMessage, setPostMessage] = useState<string>("");
  const [music, setMusic] = useState<string>("노래제목");
  const navigate = useNavigate();
  const userInfo = useSelector((state: AppState) => state.user.userInfo);

  const handlePost = () => {
    axios
      .post("http://localhost:8080/api/v1/user/requestPost", {
        title: title,
        content: content,
        board: userInfo.mbti,
      }, { withCredentials: true })
      .then((result) => {
        console.log(result.data);
        navigate("/"); // 성공 시 글목록으로 리다이렉트
      })
      .catch((error) => {
        console.log("글쓰기 오류: ", error);
        setPostMessage("글쓰기에 문제가 발생했어요.");
      });
  };

  return (
    <div className="MainPage">
      <Body>
        <h3>다른 사람들에게 노래를 추천해봐요 😀</h3>
        <TitleMusicArea>
          <MbtiArea>
            <h4>게시판</h4>
            <p>{userInfo.mbti} 게시판</p>
          </MbtiArea>

          <MusicArea>
            <button>노래 선택하기</button>
            <p>{music}</p>
          </MusicArea>
        </TitleMusicArea>

        <h4>제목</h4>
        <Input
          onChange={(e) => {
            setTitle(e.target.value);
          }}
        ></Input>
        <h4>내용</h4>
        <TextArea
          rows={20}
          onChange={(e) => {
            setContent(e.target.value);
          }}
        ></TextArea>
        <button onClick={handlePost}>글 작성하기</button>
        <p>{postMessage}</p>
      </Body>
    </div>
  );
};

export default WritePage;
