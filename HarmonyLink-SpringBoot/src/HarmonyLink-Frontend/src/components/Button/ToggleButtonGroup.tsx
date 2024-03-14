import React from 'react';
import { ButtonGroup, ToggleButton } from 'react-bootstrap';

interface Option {
  label: string | number;
  value: string | number;
  key: string | number;
}

interface ToggleButtonGroupProps {
  options: Option[];
  name: string;
  value: string | number;  
  onChange: (value: string) => void;
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
