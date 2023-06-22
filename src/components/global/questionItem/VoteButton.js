import { useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faThumbsUp, faThumbsDown } from '@fortawesome/free-solid-svg-icons';
import styled from 'styled-components';

const VoteContainer = styled.div`
  display: flex;
  align-items: center;
  margin-right: 0.3rem;
  border: 1.5px solid #002075;
  color: #002075;
  padding: 0.25rem 0.5rem;
  cursor: pointer;
  max-height: 27px;

  span {
    margin-left: 0.5rem;
    font-size: 0.7rem;
  }
  .icon {
    font-size: 0.8rem;
  }
`;

const VoteButton = ({ answerId }) => {
  const [likes, setLikes] = useState(() => {
    const storedLikes = localStorage.getItem(`likes_${answerId}`);
    return storedLikes ? Number(storedLikes) : 0;
  });

  const [dislikes, setDislikes] = useState(() => {
    const storedDislikes = localStorage.getItem(`dislikes_${answerId}`);
    return storedDislikes ? Number(storedDislikes) : 0;
  });

  const handleLikeClick = () => {
    setLikes((prevLikes) => {
      const newLikes = prevLikes + 1;
      localStorage.setItem(`likes_${answerId}`, newLikes);
      return newLikes;
    });
  };

  const handleDislikeClick = () => {
    setDislikes((prevDislikes) => {
      const newDislikes = prevDislikes + 1;
      localStorage.setItem(`dislikes_${answerId}`, newDislikes);
      return newDislikes;
    });
  };

  return (
    <>
      <VoteContainer onClick={handleLikeClick}>
        <FontAwesomeIcon icon={faThumbsUp} />
        <span>{likes}</span>
      </VoteContainer>
      <VoteContainer onClick={handleDislikeClick}>
        <FontAwesomeIcon icon={faThumbsDown} />
        <span>{dislikes}</span>
      </VoteContainer>
    </>
  );
};

export default VoteButton;
