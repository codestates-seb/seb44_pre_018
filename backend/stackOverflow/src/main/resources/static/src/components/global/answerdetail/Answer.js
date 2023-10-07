import { useState } from 'react';

import styled from 'styled-components';
import Editor from 'components/global/questionItem/Editor';
import VoteButton from 'components/global/answerdetail/VoteButton';
import AnswerDropdown from 'components/global/answerdetail/AnswerDropdown';

const AnswerContainer = styled.div`
  border-bottom: 1px solid #ccc;
  position: relative;
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
    margin-right: -0.2rem;
    margin-top: 15px;
    align-items: center;
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
    border: 2px solid #002075;
    color: #002075;
    padding: 0.5rem;
  }
`;

const Answer = ({ answer, onDeleteAnswer, onEditAnswer, setValue }) => {
  const [editMode, setEditMode] = useState(false);
  const [editedContent, setEditedContent] = useState(answer.content);
  const [bodyChecked, setBodyChecked] = useState(false);

  const handleCheckBody = (isChecked) => {
    setBodyChecked(isChecked);
  };

  const toggleEditMode = () => {
    setEditMode((prevMode) => !prevMode);
  };

  const handleContentChange = (content) => {
    setEditedContent(content);
    if (setValue) {
      setValue(content);
    }
  };

  const handleSaveEdit = () => {
    onEditAnswer(answer.answerId, editedContent);
    toggleEditMode();
  };

  const handleDeleteAnswer = () => {
    onDeleteAnswer(answer.answerId);
  };

  return (
    <AnswerContainer>
      <AnswerDropdown
        onEditAnswer={toggleEditMode}
        onDeleteAnswer={handleDeleteAnswer}
      />
      <div className="answerContainer">
        <div className="left-section">
          <img
            src={require('assets/profile_image1.jpeg')}
            alt="프로필 이미지"
          />
          <span className="username text-sm py-2">{answer.username}</span>
        </div>

        <div className="comment text-sm font-light py-2">
          {editMode ? (
            <Editor
              height="200"
              value={editedContent}
              setValue={handleContentChange}
              checkBody={handleCheckBody}
            />
          ) : (
            <p dangerouslySetInnerHTML={{ __html: answer.content }}></p>
          )}
        </div>

        <div className="right-section">
          <VoteButton answerId={answer.answerId} />
          {editMode && (
            <button
              className="pointBu03"
              type="button"
              onClick={handleSaveEdit}
            >
              Save
            </button>
          )}
        </div>
      </div>
    </AnswerContainer>
  );
};

export default Answer;
