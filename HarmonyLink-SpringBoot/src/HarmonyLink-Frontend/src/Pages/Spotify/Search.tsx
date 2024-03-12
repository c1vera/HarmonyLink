import axios from "axios";
import { useEffect, useState } from "react";
import styled from "styled-components";
import Input from "../../components/Input";
import ToggleButtonGroup from "../../components/Button/ToggleButtonGroup";
import Button from "react-bootstrap/Button";
import Table from "react-bootstrap/Table";

const Body = styled.div`
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  padding: 40px;
`;

const MusicTbody = styled.tbody`
  height: 600px;
  overflow-y: auto;
  display: block;
  width: 90%;
`
interface Music {
  trackName: string;
  artistName: string;
  imgUri: string;
}
interface MusicList {
  tracks: Music[];
}



const MainPage = () => {
  const [searchType, setSearchType] = useState<string>("track");
  const [searchValue, setSearchValue] = useState<string>("");
  const [musicList, setMusicList] = useState<MusicList | null>(null);
  const [selectMusic, setSelectMusic] = useState<Music | null>(null);
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
      <Body>
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
              <tr key={index}
              onClick={ () => setSelectMusic(music) }>
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
      </Body>
    </div>
  );
};

export default MainPage;
