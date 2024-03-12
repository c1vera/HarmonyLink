import React from 'react';
import { ButtonGroup, ToggleButton } from 'react-bootstrap';

// Option 객체에 대한 인터페이스 정의
interface Option {
  label: string | number;
  value: string | number;
  key: string | number;
}

// ToggleButtonGroup props에 대한 인터페이스 정의
interface ToggleButtonGroupProps {
  options: Option[];
  name: string;
  value: string | number;  
  onChange: (value: string) => void; // onChange 이벤트 핸들러의 타입을 정의
}

const ToggleButtonGroup: React.FC<ToggleButtonGroupProps> = ({ options, name, value, onChange }) => {
  return (
    <ButtonGroup className="custom-button-group">
      {options.map((option) => (
        <ToggleButton
          key={option.label}
          id={`radio-${option.value}`}
          type="radio"
          variant="secondary"
          name={name}
          value={option.value}
          checked={value === option.value}
          onChange={(e) => onChange(e.currentTarget.value)}
        >
          {option.label}
        </ToggleButton>
      ))}
    </ButtonGroup>
  );
};

export default ToggleButtonGroup;
