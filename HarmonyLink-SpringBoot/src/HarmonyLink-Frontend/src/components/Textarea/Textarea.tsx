import styled from "styled-components";

interface TextareaProps {
  width?: string;
  fontSize?: string;
  type?: string;
  onChange?: (event: React.ChangeEvent<HTMLInputElement>) => void;
  rows?: number;
}

const TextareaEach = styled.textarea<TextareaProps>`
  font-size: ${(props) => (props.fontSize ? props.fontSize : "20px")};
  padding: 15px;
  width: ${(props) => (props.width ? props.width : "700px")};
  border-radius: 15px;
  border: 2.5px solid #a986ff;
  resize: none;
`;

const Textarea: React.FC<TextareaProps> = (props) => {
  return <TextareaEach
  width={props.width}
  fontSize={props.fontSize}
  onChange={props.onChange}
  rows={props.rows}
  />;
};

export default Textarea;
