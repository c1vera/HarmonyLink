import styled from "styled-components";
import { useState } from "react";
import Button from "react-bootstrap/Button";
import Form from "react-bootstrap/Form";
import Modal from "react-bootstrap/Modal";

interface ModalProps extends React.InputHTMLAttributes<HTMLInputElement> {
  buttonName?: string;
  modalTitle?: string;
}

const ModalArea = styled.div`
  font-family: "omyu_pretty";
  font-size: x-large;
  text-align:center;
  align-items: center;
`;

const CustomModal: React.FC<ModalProps> = ({
  buttonName,
  modalTitle,
  children,
}) => {
  const [show, setShow] = useState(false);
  const handleClose = () => setShow(false);
  const handleShow = () => setShow(true);

  return (
    <>
      <Button variant="primary" onClick={handleShow}>
        {buttonName}
      </Button>
      <Modal size="lg" show={show} onHide={handleClose}>
        <ModalArea>
          <Modal.Header closeButton>
            <Modal.Title>{modalTitle}</Modal.Title>
          </Modal.Header>
          <Modal.Body>{children}</Modal.Body>
          <Modal.Footer>
            <Button variant="secondary" onClick={handleClose}>
              Close
            </Button>
          </Modal.Footer>
        </ModalArea>
      </Modal>
    </>
  );
};

export default CustomModal;
