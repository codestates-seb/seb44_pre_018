import { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';

import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEllipsis } from '@fortawesome/free-solid-svg-icons';
import styled from 'styled-components';

const Dropdown = styled.div`
  position: absolute;
  right: 5px;

  .dropdown-menu {
    position: absolute;
    top: 8px;
    right: -22px;
    width: max-content;
    background-color: #c2d3ff;
    border-radius: 8px;
    box-shadow: 0px 2px 8px rgba(0, 0, 0, 0.15);
    z-index: 1;
    padding: 0.5rem;
    margin-top: 1rem;
    font-size: 0.7rem;
  }

  .dropdown-menu::after {
    content: '';
    border-top: 0px solid transparent;
    border-left: 10px solid transparent;
    border-right: 10px solid transparent;
    border-bottom: 10px solid #c2d3ff;
    position: absolute;
    top: -8px;
    left: 50%;
    transform: translateX(-50%);
  }

  .dropdown-menu-item {
    padding: 0.5rem;
    cursor: pointer;
    text-align: center;
  }
`;
const ToggleButton = styled.button`
  opacity: 0.5;
  width: 2rem;
  transition: all 0.2s;
  &:hover {
    opacity: 1;
  }
`;

const QuestionEditButton = ({ id }) => {
  // const { id } = useParams();
  const navigate = useNavigate();
  const [isOpenStatus, setIsOpenStatus] = useState(false);
  const [post, setPost] = useState(id);

  const toggleDropdown = () => {
    setIsOpenStatus((prevStatus) => !prevStatus);
  };

  const moveToUpdate = () => {
    navigate(`/update/${id}`);
  };

  const handleDeleteQuestion = () => {
    if (window.confirm('삭제하시겠습니까?')) {
      fetch(`/question/delete/${id}/${1}`, {
        method: 'DELETE',
      }).then((res) => {
        if (res.ok) {
          setPost({ id: 0 });
          navigate('/');
        }
      });
    }
  };

  if (id === 0) {
    return null;
  }

  return (
    <>
      <Dropdown>
        <ToggleButton className="dropdown-toggle" onClick={toggleDropdown}>
          <FontAwesomeIcon icon={faEllipsis} />
        </ToggleButton>
        {isOpenStatus && (
          <div className="dropdown-menu">
            <div className="dropdown-menu-item" onClick={moveToUpdate}>
              수정하기
            </div>
            <div className="dropdown-menu-item" onClick={handleDeleteQuestion}>
              삭제하기
            </div>
          </div>
        )}
      </Dropdown>
    </>
  );
};

export default QuestionEditButton;
