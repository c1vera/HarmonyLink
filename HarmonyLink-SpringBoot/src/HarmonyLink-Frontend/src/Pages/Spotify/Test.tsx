import React, { useEffect, useState } from 'react';
import axios from 'axios';

// 스포티파이 트랙 정보에 대한 인터페이스 정의
interface Track {
  id: string;
  name: string;
  // 트랙에 대한 추가적인 필드들을 여기에 정의할 수 있습니다.
}

const SpotifyComponent: React.FC = () => {
  const [tracks, setTracks] = useState<Track[]>([]);

  useEffect(() => {
    const fetchTracks = async () => {
      try {
        const { data: { accessToken } } = await axios.get('/api/spotify/token'); // 서버로부터 액세스 토큰 받아오기
    
        const query = 'remaster%20track:Doxy%20artist:Miles%20Davis';
        const response = await axios.get('https://api.spotify.com/v1/search', {
          headers: {
            'Authorization': `Bearer ${accessToken}` // 액세스 토큰 설정
          },
          params: {
            q: query,
            type: 'track'
          }
        });
    
        setTracks(response.data.tracks.items); // 검색 결과 설정
      } catch (error) {
        console.error("Error fetching tracks:", error);
      }
    };    

    fetchTracks();
  }, []);

  return (
    <div>
      {tracks.map((track) => (
        <div key={track.id}>
          {track.name}
          {/* 트랙 정보 렌더링 */}
        </div>
      ))}
    </div>
  );
};

export default SpotifyComponent;
