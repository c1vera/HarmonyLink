import axios from "axios";
import { useEffect } from "react";
import styled from "styled-components";

const Body = styled.div`
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  padding: 40px;
`;

const MainPage: React.FC = () => {
  useEffect(() => {
    const fetchMusicSearch = async () => {
      try {
        const response = await axios.get(
          "http://localhost:8080/api/spotify/search", {
            params: {
                type: 'album',
                word: 'abacab'
            }
          }
        );
        console.log("검색 정보", response.data);
      } catch (error) {
        console.error("검색 에러", error);
      }
    };
    fetchMusicSearch();
  }, []);

  return (
    <div className="MainPage">
      <Body>
        <p>검색중</p>
      </Body>
    </div>
  );
};

export default MainPage;
