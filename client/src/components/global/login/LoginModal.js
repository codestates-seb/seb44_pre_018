import { useNavigate } from 'react-router-dom';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faTimes, faCheck } from '@fortawesome/free-solid-svg-icons';
import styled from 'styled-components';

const ModalWrapper = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  display: flex;
  align-items: center;
  justify-content: center;
  background-color: rgba(0, 0, 0, 0.5);
  z-index: 999;
`;

const ModalContent = styled.div`
  background-color: white;
  padding: 2rem;
  border-radius: 2%;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.2);
`;

const ModalTitle = styled.div`
  font-size: 1.1rem;
  margin-bottom: 1rem;
`;

const ModalButtons = styled.div`
  display: flex;
  justify-content: flex-end;
  margin-top: 2rem;
`;

const ModalConfirmButton = styled.button`
  background-color: #fff;
  color: #ccc;
  border: 1px solid #ccc;
  padding: 0.5rem 1rem;
  margin-right: 1rem;
  cursor: pointer;
`;

const ModalCancelButton = styled.button`
  background-color: #fff;
  color: #ccc;
  border: 1px solid #ccc;
  padding: 0.5rem 1rem;
  cursor: pointer;
`;

const LoginModal = ({ isOpen, onCancel, onConfirm,onClose }) => {
  const navigate = useNavigate();

  const handleConfirm = () => {
    navigate('/login');
  };

  return (
    <ModalWrapper className={`modal ${isOpen ? 'open' : ''}`}>
      <ModalContent className="modal-content">
        <ModalTitle className="modal-title">로그인이 필요합니다</ModalTitle>
        <div className="modal-content">로그인 페이지로 이동하시겠습니까?</div>
        <ModalButtons className="modal-buttons">
          <ModalConfirmButton
            className="modal-confirm-button pointBu02"
            onClick={handleConfirm}
          >
            <FontAwesomeIcon icon={faCheck} className="mr-[5px]" />예
          </ModalConfirmButton>
          <ModalCancelButton
            className="modal-cancel-button pointBu01"
            onClick={onCancel}
          >
            <FontAwesomeIcon icon={faTimes} className="mr-[5px]" />
            아니오
          </ModalCancelButton>
        </ModalButtons>
      </ModalContent>
    </ModalWrapper>
  );
};

export default LoginModal;
