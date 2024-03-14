import styled, { css } from "styled-components";

export type ButtonVariant = "primary" | "default";

/**
 * **label**: 버튼 텍스트
 *
 * **theme**: default, danger
 *
 * **size**: large, medium(default), small
 * */

interface ButtonProps extends React.ButtonHTMLAttributes<HTMLButtonElement> {
  label?: string;
  theme: string;
  size: string;
  onClick?: (event: React.MouseEvent<HTMLButtonElement>) => void;
  children: React.ReactNode;
}

type Size = {
  [key: string]: {
    padding: string;
    fontSize: string;
  };
};

type Theme = {
  [key: string]: {
    backgroundColor?: string;
    backgroundImage?: string;
    color: string;
    borderColor?: string;
  };
};

const sizes: Size = {
  small: {
    padding: "5px 20px",
    fontSize: "20px",
  },
  medium: {
    padding: "10px 30px",
    fontSize: "25px",
  },
  large: {
    padding: "15px 40px",
    fontSize: "30px",
  },
};

const themes: Theme = {
  default: {
    backgroundImage:
      "linear-gradient(to top left, rgb(9, 27, 58), rgb(98, 84, 174),  rgb(241, 78, 230))",
    color: "#ffffff",
    borderColor: "none",
  },
  danger: {
    backgroundColor: "#cd1b1b",
    color: "#fff",
    borderColor: "none",
  },
};

const sizeStyles = css<ButtonProps>`
  ${({ size }) => css`
    padding: ${sizes[size].padding};
    font-size: ${sizes[size].fontSize};
  `}
`;

const themeStyles = css<ButtonProps>`
  ${({ theme }) => {
    const currentTheme = themes[theme];
    let backgroundStyle = "";
    if (currentTheme.backgroundColor) {
      backgroundStyle = `background-color: ${currentTheme.backgroundColor};`;
    } else if (currentTheme.backgroundImage) {
      backgroundStyle = `background-image: ${currentTheme.backgroundImage};`;
    }

    return css`
      ${backgroundStyle}
      color: ${currentTheme.color};
    `;
  }}
`;

const ButtonEach = styled.button<ButtonProps>`
  border-radius: 6px;

  /* size */
  ${sizeStyles}

  /* theme */
  ${themeStyles}
`;

const Button = ({
  label,
  size = "medium",
  theme = "dafault",
  children,
  ...props
}: ButtonProps) => {
  return (
    <ButtonEach size={size} theme={theme} {...props}>
      {children}
    </ButtonEach>
  );
};

export default Button;
