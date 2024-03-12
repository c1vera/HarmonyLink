import styled from "styled-components";

// React.InputHTMLAttributes<HTMLInputElement>를 확장하여 InputProps 정의
interface InputProps extends React.InputHTMLAttributes<HTMLInputElement> {
  width?: string;
  fontSize?: string;
  onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void;
}

const InputEach = styled.input<InputProps>`
  font-size: ${(props) => (props.fontSize ? props.fontSize : "20px")};
  padding: 10px;
  margin: 10px;
  width: ${(props) => (props.width ? props.width : "700px")};
  border-radius: 10px;
  border: 2px solid #bebebe;
`;

const Input = ({ width, fontSize, ...props }: InputProps) => {
  return <InputEach width={width} fontSize={fontSize} {...props} />;
};

export default Input;
