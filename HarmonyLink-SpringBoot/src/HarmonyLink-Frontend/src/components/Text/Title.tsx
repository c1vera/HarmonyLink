import styled from "styled-components";

// React.InputHTMLAttributes<HTMLInputElement>를 확장하여 InputProps 정의
interface TitleProps extends React.InputHTMLAttributes<HTMLInputElement> {
  fontSize?: string;
  color?: string;
  padding?: string;
  margin?: string;
  text?: string;
}

const TextEach = styled.p<TitleProps>`
  font-size: ${(props) => (props.fontSize ? props.fontSize : "40px")};
  color: ${(props) => (props.color ? props.color : "black")};
  padding: ${(props) => (props.padding ? props.padding : "10px")};
  margin: ${(props) => (props.margin ? props.margin : "10px")};
`;

const Title = ({ fontSize,color, padding, margin, text }: TitleProps) => {
  return <TextEach fontSize={fontSize} color={color} padding={padding} margin={margin}>{text}</TextEach>
};

export default Title;
