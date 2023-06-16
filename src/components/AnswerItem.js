import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEllipsis } from '@fortawesome/free-solid-svg-icons';
import styled from 'styled-components';

import profile from '../components/assets/profile_image1.jpeg';
import LikeButton from '../components/global/LikeButton';

const Answer = styled.div`
  margin-top: 2rem;
  border-bottom: 1px solid #ccc;
  border-top: 1px solid #ccc;
  .answerContainer {
    display: flex;
    align-items: center;
    margin-top: 1rem;
    margin-bottom: 1rem;
  }
  .left-section {
    display: flex;
    align-items: center;
    flex-direction: column;
    margin-right: 8px;
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
  .right-section {
    display: flex;
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
    display: inline-block;
  }

  .dropdown-menu {
    position: absolute;
    top: 100%;
    right: 0;
    min-width: 10rem;
    background-color: white;
    box-shadow: 0px 8px 16px rgba(0, 0, 0, 0.2);
    z-index: 1;
    padding: 0.5rem;
    display: none;
  }

  .dropdown:hover .dropdown-menu {
    display: block;
  }

  .dropdown-menu-item {
    padding: 0.5rem;
    cursor: pointer;
  }
`;

const AnswerItem = () => {
  const [dropdownOpen, setDropdownOpen] = useState(false);

  const toggleDropdown = () => {
    setDropdownOpen(!dropdownOpen);
  };

  return (
    <Answer>
      <div className="answerContainer">
        <div className="left-section">
          <img src={profile} alt="프로필 이미지" />
          <span className="username text-sm py-2">냥냥씨</span>
        </div>
        <div className="comment text-sm font-light py-2">
          I have a list of bean objects passed into my JSP page, and one of them
          is a comment field. <br />
          This field may contain newlines, and I want to replace them with
          semicolons using JSTL, <br />
          so that the field can be displayed in a text input. I have found one
          solution, but it&apos;s not very elegant.
          <br />
          I&apos;ll post below as a possibility.
        </div>

        <div className="right-section">
          <LikeButton />

          <div className="dropdown">
            <button className="dropdown-toggle" onClick={toggleDropdown}>
              <FontAwesomeIcon icon={faEllipsis} />
            </button>
            {dropdownOpen && (
              <div className="dropdown-menu">
                <div className="dropdown-menu-item">수정</div>
                <div className="dropdown-menu-item">삭제</div>
              </div>
            )}
          </div>
        </div>
      </div>
    </Answer>
  );
};

export default AnswerItem;
