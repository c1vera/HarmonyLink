import { useState, useEffect } from "react";
import axios from "axios";
import Table from "react-bootstrap/Table";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import ToggleButtonGroup from "../../components/Button/ToggleButtonGroup";
import * as S from "../../style/GlobalStyles";

const MbtiArea = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 40px;
`;

const MbtiBtnArea = styled.div`
  margin: 20px;
`;

const TrackImg = styled.img`
  width: 40px;
  height: 40px;
`;

const PostsArea = styled.div`
  width: 90%;
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
  view: number;
  trackName: string | null;
  imgUri: string | null;
}

interface PostList {
  content: Post[];
}

const PostBoard: React.FC = () => {
  // 상태의 타입을 Post 배열로 설정
  const [postList, setPostList] = useState<PostList | null>(null);
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);

  const [radioValueEI, setRadioValueEI] = useState("X1");
  const [radioValueSN, setRadioValueSN] = useState("X2");
  const [radioValueTF, setRadioValueTF] = useState("X3");
  const [radioValueJP, setRadioValueJP] = useState("X4");

  // 총 게시글 개수
  const [totalCount, setTotalCount] = useState(null);

  // 선택된 페이지
  const [currentPage, setCurrentPage] = useState<string>("1");
  const handleChange = (value: string) => {
    setCurrentPage(value);
  };

  // 총 페이지 수
  const optionNumber = totalCount ? Math.ceil(totalCount / 10) : 0;
  const options = [];

  const showPageCnt = 5;

  if (optionNumber) {
    if (Number(currentPage) <= showPageCnt - 2) {
      // 선택된 페이지가 3보다 작을때
      if (optionNumber < showPageCnt) {
        for (let i = 1; i <= optionNumber; i++) {
          options.push({ label: `${i}`, value: `${i}`, key: `${i}` });
        }
      } else {
        for (let i = 1; i <= showPageCnt; i++) {
          options.push({ label: `${i}`, value: `${i}`, key: `${i}` });
        }
        options.push({
          label: ">",
          value: `${Number(currentPage) + 10}`,
          key: `${Number(currentPage) + 10}`,
        });
        options.push({
          label: ">>",
          value: `${optionNumber}`,
          key: `${optionNumber}`,
        });
      }
    } else if (Number(currentPage) + 2 >= optionNumber) {
      // 선택된 페이지 + 2가 전체 페이지수보다
      options.push({
        label: "<<",
        value: "1",
        key: "1",
      });
      options.push({
        label: "<",
        value: `${Number(currentPage) - 10}`,
        key: `${Number(currentPage) - 10}`,
      });
      for (let i = optionNumber - 4; i <= optionNumber; i++) {
        options.push({ label: `${i}`, value: `${i}`, key: `${i}` });
      }
    } else if (Number(currentPage) > showPageCnt - 2) {
      // 선택된 페이지가 5보다 클때
      options.push({
        label: "<<",
        value: "1",
        key: "1",
      });
      options.push({
        label: "<",
        value: `${Number(currentPage) - 10}`,
        key: `${Number(currentPage) - 10}`,
      });

      for (let i = Number(currentPage) - 2; i <= Number(currentPage) + 2; i++) {
        options.push({ label: `${i}`, value: `${i}`, key: `${i}` });
      }

      options.push({
        label: ">",
        value: `${Number(currentPage) + 10}`,
        key: `${Number(currentPage) + 10}`,
      });
      options.push({
        label: ">>",
        value: `${optionNumber}`,
        key: `${optionNumber}`,
      });
    }

    if (Number(currentPage) < 0) {
      setCurrentPage("1");
    } else if (Number(currentPage) > optionNumber)
      setCurrentPage(optionNumber.toString());
  }

  const handleOpenPost = async (postKey: number) => {
    try {
      const response = await axios.get(
        `http://localhost:8080/api/v1/user/post/${postKey}`
      );
      // navigate 호출 시 response.data를 직접 전달
      navigate(`/detail/${postKey}`, { state: { post: response.data } });
      console.log(response);
    } catch (error) {
      console.error("글을 불러오지 못했어요", error);
    }
  };

  useEffect(() => {
    const fetchData = async () => {
      setIsLoading(true);
      const EI = radioValueEI.replace("X1", "X");
      const SN = radioValueSN.replace("X2", "X");
      const TF = radioValueTF.replace("X3", "X");
      const JP = radioValueJP.replace("X4", "X");
      // 모든 상태 값을 조합하여 최종 MBTI 값을 생성
      const mbtiVal = `${EI}${SN}${TF}${JP}`;

      console.log("필터링 get 요청 보낼 mbti ->", mbtiVal); // 결과 출력

      try {
        const response = await axios.get(
          `http://localhost:8080/api/v1/user/postListFiltered/${mbtiVal}`,
          {
            params: {
              page: Number(currentPage) - 1,
              size: 10,
            },
          }
        );
        setPostList(response.data);
        setTotalCount(response.data.totalElements);
        console.log(mbtiVal, "필터된 글목록", response);
      } catch (error) {
        console.error("요청 중 오류 발생:", error);
      }
      setIsLoading(false);
    };

    fetchData();
  }, [radioValueEI, radioValueSN, radioValueTF, radioValueJP, currentPage]);

  useEffect(() => {
    setCurrentPage("1");
  }, [radioValueEI, radioValueSN, radioValueTF, radioValueJP]);

  return (
    <div className="MainPage">
      <S.Body>
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
        {isLoading ? (
          <p>로딩중</p>
        ) : (
          <PostsArea>
            <Table bordered hover>
              <thead>
                <tr>
                  <th>Number</th>
                  <th>MBTI</th>
                  <th>Title</th>
                  <th>User</th>
                  <th>Music</th>
                  <th>Img</th>
                  <th>View</th>
                  <th>💜</th>
                </tr>
              </thead>
              <tbody>
                {postList?.content?.map((post: Post) => (
                  <tr
                    key={post.post_key}
                    onClick={() => handleOpenPost(post.post_key)}
                  >
                    <td>{post.post_key}</td>
                    <td>{post.type}</td>
                    <td>{post.title}</td>
                    <td>{post.nickname}</td>
                    <td>{post.trackName || "없음"}</td>
                    <td>
                      <TrackImg src={post.imgUri || "null"} />
                    </td>
                    <td>{post.view}</td>
                    <td>{post.thumbsUp}</td>
                  </tr>
                ))}
              </tbody>
            </Table>
            <ToggleButtonGroup
              options={options}
              name="page"
              value={currentPage}
              onChange={handleChange}
            />
          </PostsArea>
        )}
      </S.Body>
    </div>
  );
};

export default PostBoard;
