import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEllipsis } from '@fortawesome/free-solid-svg-icons';
import styled from 'styled-components';
import AnswerDeleteModal from 'components/global/answerdetail/AnswerDeleteModal';

const ToggleButton = styled.button`
  opacity: 0.5;
  width: 2rem;
  transition: all 0.2s;
  &:hover {
    opacity: 1;
  }
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
        <ToggleButton className="dropdown-toggle" onClick={toggleDropdown}>
          <FontAwesomeIcon icon={faEllipsis} />
        </ToggleButton>
        {isOpenStatus && (
          <div className="dropdown-menu">
            <div className="dropdown-menu-item" onClick={handleEditAnswer}>
              수정하기
            </div>
            <div className="dropdown-menu-item" onClick={handleDeleteAnswer}>
              삭제하기
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
