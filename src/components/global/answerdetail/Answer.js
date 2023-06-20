import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEllipsis } from '@fortawesome/free-solid-svg-icons';
import styled from 'styled-components';
import Editor from 'components/global/questionItem/Editor';
import VoteButton from 'components/global/questionItem/VoteButton';

const AnswerContainer = styled.div`
  border-bottom: 1px solid #ccc;
  .answerContainer {
    display: flex;
    align-items: center;
    justify-content: space-between;
    margin-top: 1rem;
    margin-bottom: 1rem;
  }
  
  .left-section {
    display: flex;
    align-items: center;
    flex-direction: column;
  }

  .right-section {
    display: flex;
  }
  
  img {
    border-radius: 50%;
    width: 3rem;
    height: 3rem;
    margin-right: 8px;
    box-shadow: 0px 8px 8px rgba(0, 0, 0, 0.1);
  }

  .username {
    display: flex;
    align-items: center;
  }

  .comment {
    flex: 1;
  }
  

  .likes-container,
  .dislikes-container {
    display: flex;
    align-items: center;
    margin-right: 1rem;
    border: 2px solid #002075;
    color: #002075;
    padding: 0.5rem;
  }

  .dropdown {
    position: relative;
  }

  .dropdown-menu {
  position: absolute;
  top: calc(100% + 8px);
  right: 0;
  width: max-content;
  background-color: #c2d3ff;
  border-radius: 8px;
  box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.15);
  z-index: 1;
  padding: 0.5rem;
  margin-top: 1.6rem;
}

.dropdown-menu::after {
  content: '';
  border-top:0px solid transparent; 
 border-left: 10px solid transparent; 
 border-right: 10px solid transparent; 
 border-bottom: 10px solid #c2d3ff;
 position:absolute;
 top:-8px;
 right:12px;  
}

.dropdown-menu-item {
  padding: 0.5rem;
  cursor: pointer;
  text-align: center;
}
`;

const ToggleButton = styled.button`
  position: relative;
  opacity: 0;
  transition: opacity 0.3s;
  position: absolute;
  top: 20%;
  right: 0;
  transform: translate(50%, -50%); 
  margin-right: 1rem;
  margin-top: 1rem;
  width: 2rem;

  .dropdown:hover & {
    opacity: 1;
  }
`;

const Answer = ({ answer, onDeleteAnswer, onEditAnswer }) => {
  const [dropdownOpen, setDropdownOpen] = useState(false);
  const [editMode, setEditMode] = useState(false);
  const [editedContent, setEditedContent] = useState(answer.content);

  const toggleDropdown = () => {
    setDropdownOpen((prevState) => !prevState);
  };

  const toggleEditMode = () => {
    setEditMode((prevMode) => !prevMode);
  };

  const handleContentChange = (content) => {
    setEditedContent(content);
  };

  const handleSaveEdit = () => {
    onEditAnswer(answer.answerId, editedContent);
    toggleEditMode();
  };

  const handleDeleteAnswer = () => {
    console.log('Delete answer:', answer.answerId);
    onDeleteAnswer(answer.answerId);
  };

  return (
    <AnswerContainer>
      <div className="dropdown">
        <ToggleButton className="dropdown-toggle" onClick={toggleDropdown} isOpen={dropdownOpen}>
          <FontAwesomeIcon icon={faEllipsis} />
        </ToggleButton>
        {dropdownOpen && (
          <div className="dropdown-menu">
            <div className="dropdown-menu-item" onClick={toggleEditMode}>
              댓글 수정
            </div>
            <div className="dropdown-menu-item" onClick={handleDeleteAnswer}>
              댓글 삭제
            </div>
          </div>
        )}
      </div>
      <div className="answerContainer">
        <div className="left-section">
          <img src={require('assets/profile_image1.jpeg')} alt="프로필 이미지" />
          <span className="username text-sm py-2">{answer.username}</span>
        </div>
        <div className="comment text-sm font-light py-2">
          {editMode ? (
            <Editor height="200" value={editedContent} onChange={handleContentChange} />
          ) : (
            <p dangerouslySetInnerHTML={{ __html: answer.content }}></p>
          )}
        </div>
        <div className="right-section">
          <VoteButton answerId={answer.answerId} />
          {editMode && (
            <button className="pointBu03" type="button" onClick={handleSaveEdit}>
              Save
            </button>
          )}
        </div>
      </div>
    </AnswerContainer>
  );
};

export default Answer;