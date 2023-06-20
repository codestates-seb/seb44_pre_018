import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEllipsis } from '@fortawesome/free-solid-svg-icons';
import styled from 'styled-components';

const ToggleButton = styled.button`
  position: relative;
  opacity: 0;
  transition: opacity 0.3s;
  position: absolute;
  top: 100%;
  right: 0;
  transform: translate(80%, -80%); 
  margin-right: 1rem;
  margin-top: 1rem;
  width: 2rem;

  .dropdown:hover & {
    opacity: 1;
  }
`;

const AnswerDropdown = ({ onEditAnswer, onDeleteAnswer }) => {
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const toggleDropdown = () => {
    setDropdownOpen((prevState) => !prevState);
  };

  const handleEditAnswer = () => {
    onEditAnswer();
    toggleDropdown();
  };

  const handleDeleteAnswer = () => {
    onDeleteAnswer();
    toggleDropdown();
  };

  return (
    <div className="dropdown">
      <ToggleButton className="dropdown-toggle" onClick={toggleDropdown} isOpen={dropdownOpen}>
        <FontAwesomeIcon icon={faEllipsis} />
      </ToggleButton>
      {dropdownOpen && (
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
  );
};

export default AnswerDropdown;