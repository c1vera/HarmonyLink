import styled from "styled-components";
import Input from "../../components/Input";
import TextArea from "../../components/Textarea/Textarea";
import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import { useSelector } from "react-redux";
import { AppState } from "../../redux/store";
import Modal from "../../components/Modal";
import ToggleButtonGroup from "../../components/Button/ToggleButtonGroup";
import Button from "react-bootstrap/Button";
import Table from "react-bootstrap/Table";
import * as S from "../../style/GlobalStyles"

const TitleMusicArea = styled.div`
  display: grid;
  grid-template-columns: 1fr 1fr;
  align-items: center;
`;

const MbtiArea = styled.div`
  display: flex;
  flex-grow: 0;
  align-items: center;
  > * {
    /* 직계 자식 요소만 선택 */
    margin: 0px 30px;
  }
`;
const MusicArea = styled.div`
  display: flex;
  flex-grow: 0;
  align-items: center;
  > * {
    /* 직계 자식 요소만 선택 */
    margin: 0px 30px;
  }
`;

const MusicTbody = styled.tbody`
  height: 600px;
  overflow-y: auto;
  display: block;
  width: 90%;
`;
interface Music {
  trackName: string;
  artistName: string;
  imgUri: string;
}
interface MusicList {
  tracks: Music[];
}

const WritePage: React.FC = () => {
  const [title, setTitle] = useState<string>("");
  const [content, setContent] = useState<string>("");
  const [postMessage, setPostMessage] = useState<string>("");
  const navigate = useNavigate();
  const userInfo = useSelector((state: AppState) => state.user.userInfo);

  const [searchType, setSearchType] = useState<string>("track");
  const [searchValue, setSearchValue] = useState<string>("");
  const [musicList, setMusicList] = useState<MusicList | null>(null);
  const [selectMusic, setSelectMusic] = useState<Music | null>(null);
  const [modalShow, setModalShow] = useState(false);

  const handleClose = () => {
    setMusicList(null);
    setModalShow(false);
  }
  const handleShow = () => setModalShow(true);

  const handlePost = async () => {
    try {
      const result = await axios.post(
        "http://localhost:8080/api/v1/user/requestPost",
        {
          title: title,
          content: content,
          type: userInfo?.mbti,
          trackName: selectMusic?.trackName,
          artistName: selectMusic?.artistName,
          imgUri: selectMusic?.imgUri,
        },
        { withCredentials: true }
      );

      console.log(result.data);
      navigate("/"); // 성공 시 글목록으로 리다이렉트
    } catch (error) {
      console.log("글쓰기 오류: ", error);
      setPostMessage("글쓰기에 문제가 발생했어요.");
    }
  };

  // 노래 검색 로직
  const fetchMusicSearch = async () => {
    try {
      const response = await axios.get(
        "http://localhost:8080/api/spotify/search",
        {
          params: {
            type: searchType,
            word: searchValue,
          },
        }
      );
      setMusicList(response.data);
      console.log("검색 정보", response.data);
      console.log(musicList);
    } catch (error) {
      console.error("검색 에러", error);
    }
  };

  return (
    <div className="MainPage">
      <S.Body>
        <h3>다른 사람들에게 노래를 추천해봐요 😀</h3>
        <TitleMusicArea>
          <MbtiArea>
            <h4>게시판</h4>
            <p>{userInfo?.mbti} 게시판</p>
          </MbtiArea>

          <MusicArea>
            <Button variant="secondary" onClick={handleShow}>
              노래 선택하기
            </Button>
            <Modal
              show={modalShow}
              onClose={handleClose}
              modalTitle="노래 검색"
            >
              <form>
                <ToggleButtonGroup
                  options={[
                    { label: "Track", value: "track", key: "track" },
                    { label: "Artist", value: "artist", key: "artist" },
                  ]}
                  name="radioEI"
                  value={searchType}
                  onChange={setSearchType}
                />
                <Input
                  width="300px"
                  onChange={(e) => {
                    setSearchValue(e.target.value);
                  }}
                  onKeyPress={(e) => {
                    if (e.key === "Enter") {
                      fetchMusicSearch();
                    }
                  }}
                />
                <Button variant="secondary" onClick={fetchMusicSearch}>
                  검색하기
                </Button>

                <Table bordered hover>
                  <MusicTbody>
                    {musicList?.tracks?.map((music, index) => (
                      <tr
                        key={index}
                        onClick={() => {
                          setSelectMusic(music);
                          handleClose();
                        }}
                      >
                        <td>
                          <img
                            src={music.imgUri}
                            alt="Album Cover"
                            style={{ width: "50px", height: "50px" }}
                          />
                        </td>
                        <td>{music.trackName}</td>
                        <td>{music.artistName}</td>
                      </tr>
                    ))}
                  </MusicTbody>
                </Table>
              </form>
            </Modal>
            <p>{selectMusic?.trackName}</p>
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
      </S.Body>
    </div>
  );
};

export default WritePage;
