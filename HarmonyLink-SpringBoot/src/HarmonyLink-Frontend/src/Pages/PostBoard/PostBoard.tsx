import styled from "styled-components";

const Body = styled.div`
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
`;

const PostBoard: React.FC = () => {
  return (
    <div className="MainPage">
      <Body>
        <h4>글목록</h4>
      </Body>
    </div>
  );
};

export default PostBoard;
