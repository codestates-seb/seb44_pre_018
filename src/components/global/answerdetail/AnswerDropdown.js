import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEllipsis } from '@fortawesome/free-solid-svg-icons';
import styled from 'styled-components';
import AnswerDeleteModal from 'components/global/answerdetail/AnswerDeleteModal';

const ToggleButton = styled.button`
  position: absolute;
  opacity: 1;
  top: 100%;
  right: 0;
  transform: translate(80%, -160%); 
  margin-right: 1rem;
  margin-top: 1rem;
  width: 2rem;
`;

const AnswerDropdown = ({ onEditAnswer, onDeleteAnswer }) => {
  const [isOpenStatus, setIsOpenStatus] = useState(false);
  const [showConfirmationModal, setShowConfirmationModal] = useState(false);



  const toggleDropdown = () => {
    setIsOpenStatus((prevStatus) => !prevStatus);
  };

  const handleEditAnswer = () => {
    onEditAnswer();
    toggleDropdown();
  };

  const handleDeleteAnswer = () => {
    setShowConfirmationModal(true);
    toggleDropdown();
  };

  const handleCancelDelete = () => {
    setShowConfirmationModal(false);
  };

  const handleConfirmDelete = () => {
    setShowConfirmationModal(false);
    onDeleteAnswer();
  };

  return (
    <>
    <div className="dropdown">
      <ToggleButton className="dropdown-toggle" onClick={toggleDropdown} >
        <FontAwesomeIcon icon={faEllipsis} />
      </ToggleButton>
      {isOpenStatus && (
        <div className="dropdown-menu">
          <div className="dropdown-menu-item" onClick={handleEditAnswer}>
            댓글 수정
          </div>
          <div className="dropdown-menu-item" onClick={handleDeleteAnswer}>
            댓글 삭제
          </div>
        </div>
      )}
    </div>
    {showConfirmationModal && (
        <AnswerDeleteModal
        isOpen={true}
          onCancel={handleCancelDelete}
          onConfirm={handleConfirmDelete}
        />
      )}
  </>
  );
};

export default AnswerDropdown;