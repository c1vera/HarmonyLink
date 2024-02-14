import styled from "styled-components";
import './RegisterPage.css'
import { useState } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";
import Input from "../../components/Input/Input";
import ButtonGroup from "react-bootstrap/ButtonGroup";
import ToggleButton from "react-bootstrap/ToggleButton";

const Body = styled.div`
  height: 100%;
  display: flex;
  flex-direction: column;
  align-items: center;
  text-align: center;
  margin:40px;
`;

const MbtiArea = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 40px;
`;

const MbtiBtnArea = styled.div`
margin: 20px;
`;

const RegisterPage: React.FC = () => {
  const [id, setId] = useState<string>("");
  const [pw, setPw] = useState<string>("");
  const [email, setEmail] = useState<string>("");
  const [name, setName] = useState<string>("");
  const [nickname, setNickname] = useState<string>("");
  const [registerMassege, setRegisterMassege] = useState<string>("");
  const navigate = useNavigate();

  const [radioValueEI, setRadioValueEI] = useState("E");
  const [radioValueSN, setRadioValueSN] = useState("S");
  const [radioValueTF, setRadioValueTF] = useState("T");
  const [radioValueJP, setRadioValueJP] = useState("J");
  /** 회원가입 POST 요청 */
  const handleRegister = () => {
    const mbtiVal = radioValueEI + radioValueSN + radioValueTF + radioValueJP;
    axios
      .post("http://localhost:8080/api/v1/user/Register", {
        id: id,
        pw: pw,
        email: email,
        name: name,
        nickname: nickname,
        mbti: mbtiVal,
      })
      .then((result) => {
        console.log(result.data);
        navigate("/"); // 성공 시 홈으로 리다이렉트
      })
      .catch((error) => {
        console.log("회원가입 데이터 전송오류: ", error);
        setRegisterMassege("회원가입에 문제가 발생했어요.");
      });
  };

  return (
    <div className="MainPage">
      <Body>
        <h3>회원가입</h3>
        <p>아이디</p>
        <Input
          type="text"
          onChange={(e) => {
            setId(e.target.value);
          }}
        ></Input>
        <p>비밀번호</p>
        <Input
          type="text"
          onChange={(e) => {
            setPw(e.target.value);
          }}
        ></Input>
        <p>이메일</p>
        <Input
          type="text"
          onChange={(e) => {
            setEmail(e.target.value);
          }}
        ></Input>
        <p>이름</p>
        <Input
          type="text"
          onChange={(e) => {
            setName(e.target.value);
          }}
        ></Input>
        <p>닉네임</p>
        <Input
          type="text"
          onChange={(e) => {
            setNickname(e.target.value);
          }}
        ></Input>
        {/* 라디오버튼으로 구현 예정 */}
        <p>MBTI</p>
        <MbtiArea>
          <MbtiBtnArea>
            <ButtonGroup className="custom-button-group">
              <ToggleButton className="custom-toggle-button"
                id="radio-e"
                type="radio"
                variant="secondary"
                name="radioEI"
                value="E"
                checked={radioValueEI === "E"} // 'E'가 현재 선택된 값인지 확인
                onChange={(e) => setRadioValueEI(e.currentTarget.value)} // 값 변경 시 radioValue 업데이트
              >
                E
              </ToggleButton>
              <ToggleButton className="custom-toggle-button"
                id="radio-i"
                type="radio"
                variant="secondary"
                name="radioEI"
                value="I"
                checked={radioValueEI === "I"} 
                onChange={(e) => setRadioValueEI(e.currentTarget.value)} // 값 변경 시 radioValue 업데이트
              >
                I
              </ToggleButton>
            </ButtonGroup>
            <ButtonGroup className="custom-button-group">
              <ToggleButton className="custom-toggle-button"
                id="radio-s"
                type="radio"
                variant="secondary"
                name="radioSN"
                value="S"
                checked={radioValueSN === "S"} 
                onChange={(e) => setRadioValueSN(e.currentTarget.value)}
              >
                S
              </ToggleButton>
              <ToggleButton className="custom-toggle-button"
                id="radio-n"
                type="radio"
                variant="secondary"
                name="radioSN"
                value="N"
                checked={radioValueSN === "N"} 
                onChange={(e) => setRadioValueSN(e.currentTarget.value)}
              >
                N
              </ToggleButton>
            </ButtonGroup>
            <ButtonGroup className="custom-button-group">
              <ToggleButton className="custom-toggle-button"
                id="radio-t"
                type="radio"
                variant="secondary"
                name="radioTF"
                value="T"
                checked={radioValueTF === "T"} 
                onChange={(e) => setRadioValueTF(e.currentTarget.value)}
              >
                T
              </ToggleButton>
              <ToggleButton className="custom-toggle-button"
                id="radio-f"
                type="radio"
                variant="secondary"
                name="radioTF"
                value="F"
                checked={radioValueTF === "F"} 
                onChange={(e) => setRadioValueTF(e.currentTarget.value)}
              >
                F
              </ToggleButton>
            </ButtonGroup>
            <ButtonGroup className="custom-button-group">
              <ToggleButton className="custom-toggle-button"
                id="radio-j"
                type="radio"
                variant="secondary"
                name="radioJP"
                value="J"
                checked={radioValueJP === "J"} 
                onChange={(e) => setRadioValueJP(e.currentTarget.value)}
              >
                J
              </ToggleButton>
              <ToggleButton className="custom-toggle-button"
                id="radio-p"
                type="radio"
                variant="secondary"
                name="radioJP"
                value="P"
                checked={radioValueJP === "P"}
                onChange={(e) => setRadioValueJP(e.currentTarget.value)}
              >
                P
              </ToggleButton>
            </ButtonGroup>
          </MbtiBtnArea>
          
        </MbtiArea>
        <button onClick={handleRegister}>회원가입하기</button>
        <p>{registerMassege}</p>
      </Body>
    </div>
  );
};

export default RegisterPage;
