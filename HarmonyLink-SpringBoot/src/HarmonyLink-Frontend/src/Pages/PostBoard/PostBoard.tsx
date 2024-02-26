import { useState, useEffect } from "react";
import axios from "axios";
import Table from "react-bootstrap/Table";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import ToggleButtonGroup from "../../components/Button/ToggleButtonGroup";

const Body = styled.div`
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  padding: 40px;
`;

const MbtiArea = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 40px;
`;

const MbtiBtnArea = styled.div`
  margin: 20px;
`;

// 포스트 타입 정의
interface Post {
  post_key: number;
  title: string;
  userKey: number;
  nickname: string;
  thumbsUp: number;
  music_key: string | null;
  type: string;
}

const PostBoard: React.FC = () => {
  // 상태의 타입을 Post 배열로 설정
  const [postList, setPostList] = useState<Post[]>([]);
  const navigate = useNavigate();

  const [radioValueEI, setRadioValueEI] = useState("X1");
  const [radioValueSN, setRadioValueSN] = useState("X2");
  const [radioValueTF, setRadioValueTF] = useState("X3");
  const [radioValueJP, setRadioValueJP] = useState("X4");

  useEffect(() => {
    const fetchPostList = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/api/v1/user/postList"
        );
        setPostList(response.data); // 데이터를 받아서 상태를 업데이트
        console.log("전체 글목록", response.data);
      } catch (error) {
        console.error("글목록을 불러오지 못했어요", error);
      }
    };

    fetchPostList();
  }, []); // 빈 의존성 배열을 전달하여 컴포넌트 마운트 시에만 fetchPostList가 호출

  const handleOpenPost = async (postKey) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/v1/user/post/${postKey}`
      );
      // navigate 호출 시 response.data를 직접 전달
      navigate(`/detail/${postKey}`, { state: { post: response.data } });
    } catch (error) {
      console.error("글을 불러오지 못했어요", error);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      const EI = radioValueEI.replace("X1", "X")
      const SN = radioValueSN.replace("X2", "X")
      const TF = radioValueTF.replace("X3", "X")
      const JP = radioValueJP.replace("X4", "X")
      // 모든 상태 값을 조합하여 최종 MBTI 값을 생성
      const mbtiVal = `${EI}${SN}${TF}${JP}`;

      console.log("필터링 get 요청 보낼 mbti ->", mbtiVal); // 결과 출력

      try {
        const response = await axios.get(
          `http://localhost:8080/api/v1/user/postListFiltered/${mbtiVal}`
        );
        setPostList(response.data);
        console.log(mbtiVal, "필터된 글목록", response.data);
      } catch (error) {
        console.error("요청 중 오류 발생:", error);
      }
    };

    fetchData();
  }, [radioValueEI, radioValueSN, radioValueTF, radioValueJP]);

  return (
    <div className="MainPage">
      <Body>
        <h2>글목록</h2>

        <p>MBTI</p>
        <MbtiArea>
          <MbtiBtnArea>
            <ToggleButtonGroup
              options={[
                { label: "E", value: "E", key: "E" },
                { label: "I", value: "I", key: "I" },
                { label: "X", value: "X1", key: "X1" },
              ]}
              name="radioEI"
              value={radioValueEI}
              onChange={setRadioValueEI}
            />
            <ToggleButtonGroup
              options={[
                { label: "S", value: "S", key: "S" },
                { label: "N", value: "N", key: "N" },
                { label: "X", value: "X2", key: "X2" },
              ]}
              name="radioSN"
              value={radioValueSN}
              onChange={setRadioValueSN}
            />
            <ToggleButtonGroup
              options={[
                { label: "T", value: "T", key: "T" },
                { label: "F", value: "F", key: "F" },
                { label: "X", value: "X3", key: "X3" },
              ]}
              name="radioTF"
              value={radioValueTF}
              onChange={setRadioValueTF}
            />
            <ToggleButtonGroup
              options={[
                { label: "J", value: "J", key: "J" },
                { label: "P", value: "P", key: "P" },
                { label: "X", value: "X4", key: "X4" },
              ]}
              name="radioJP"
              value={radioValueJP}
              onChange={setRadioValueJP}
            />
          </MbtiBtnArea>
        </MbtiArea>

        <Table bordered hover>
          <thead>
            <tr>
              <th>글번호</th>
              <th>게시판</th>
              <th>제목</th>
              <th>작성자</th>
              <th>노래 제목</th>
              <th>💜</th>
            </tr>
          </thead>
          <tbody>
            {postList.map((post) => (
              <tr
                key={post.post_key}
                onClick={() => handleOpenPost(post.post_key)}
              >
                <td>{post.post_key}</td>
                <td>{post.type}</td>
                <td>{post.title}</td>
                <td>{post.nickname}</td>
                <td>{post.music_key || "없음"}</td>
                <td>{post.thumbsUp}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      </Body>
    </div>
  );
};

export default PostBoard;
