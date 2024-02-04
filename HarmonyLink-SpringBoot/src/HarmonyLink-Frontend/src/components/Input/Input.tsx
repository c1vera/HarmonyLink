import React from 'react';
import styled from "styled-components";

// React.InputHTMLAttributes<HTMLInputElement>를 확장하여 InputProps 정의
interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  width?: string;
  fontSize?: string;
}

const InputEach = styled.input<InputProps>`
  font-size: ${(props) => (props.fontSize ? props.fontSize : "20px")};
  padding: 20px;
  width: ${(props) => (props.width ? props.width : "700px")};
  border-radius: 15px;
  border: 2.5px solid #a986ff;
`;

const Input: React.FC<InputProps> = (props) => {
  return <InputEach {...props} />;
};

export default Input;
