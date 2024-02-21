import { useState, useEffect } from "react";
import axios from "axios";
import Table from "react-bootstrap/Table";
import styled from "styled-components";

const Body = styled.div`
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  margin: 40px;
`;

// 포스트 타입 정의
interface Post {
  post_key: number;
  title: string;
  userKey: number;
  thumbsUp: number;
  music_key: string | null;
}

const PostBoard: React.FC = () => {
  // 상태의 타입을 Post 배열로 설정
  const [postList, setPostList] = useState<Post[]>([]);

  useEffect(() => {
    const fetchPostList = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/api/v1/user/postList"
        );
        setPostList(response.data); // 데이터를 받아서 상태를 업데이트
        console.log(response.data);
      } catch (error) {
        console.error("글목록을 불러오지 못했어요", error);
      }
    };

    fetchPostList();
  }, []); // 빈 의존성 배열을 전달하여 컴포넌트 마운트 시에만 fetchPostList가 호출

  return (
    <div className="MainPage">
      <Body>
        <h2>글목록</h2>
        <Table bordered hover>
          <thead>
            <tr>
              <th>글번호</th>
              <th>제목</th>
              <th>작성자</th>
              <th>노래 제목</th>
              <th>💜</th>              
            </tr>
          </thead>
          <tbody>
            {postList.map((post) => (
              <tr key={post.post_key}>
                <td>{post.post_key}</td>
                <td>{post.title}</td>
                <td>{post.userKey}</td>
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
