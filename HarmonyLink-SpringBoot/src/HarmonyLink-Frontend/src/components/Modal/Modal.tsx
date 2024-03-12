import styled from "styled-components";
import { useState } from "react";
import Button from "react-bootstrap/Button";
import Modal from "react-bootstrap/Modal";

const ModalArea = styled.div`
  font-family: "omyu_pretty";
  font-size: x-large;
  text-align: center;
  align-items: center;
`;

interface ModalProps extends React.InputHTMLAttributes<HTMLInputElement> {
  buttonName?: string;
  modalTitle?: string;
  show: boolean;
  onClose: () => void;
}

const CustomModal: React.FC<ModalProps> = ({
  modalTitle,
  children,
  show,
  onClose,
}) => {
  return (
    /**
    const [modalShow, setModalShow] = useState(false);
    const handleClose = () => setModalShow(false);
    const handleShow = () => setModalShow(true);

    <Button variant="secondary" onClick={}>
      {}
    </Button>
     */
    
    <Modal size="lg" show={show} onHide={onClose}>
      <ModalArea>
        <Modal.Header closeButton>
          <Modal.Title>{modalTitle}</Modal.Title>
        </Modal.Header>
        <Modal.Body>{children}</Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={onClose}>
            Close
          </Button>
        </Modal.Footer>
      </ModalArea>
    </Modal>
  );
};

export default CustomModal;
