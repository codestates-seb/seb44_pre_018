import { useState } from 'react';
import axios from 'axios';
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

const updateCommentLikes = async () => {
  try {
    await axios.patch(`/v1/comment/like/${2}`);
  } catch (error) {
    console.error(error);
  }
};

const updateCommentDislikes = async () => {
  try {
    await axios.patch(`/v1/comment/dislike/${2}`);
  } catch (error) {
    console.error(error);
  }
};

const VoteButton = ({ commentId }) => {
  const [likes, setLikes] = useState(0);
  const [dislikes, setDislikes] = useState(0);

  const handleLikeClick = () => {
    setLikes((prevLikes) => {
      const newLikes = prevLikes + 1;
      updateCommentLikes(commentId);
      return newLikes;
    });
  };

  const handleDislikeClick = () => {
    setDislikes((prevDislikes) => {
      const newDislikes = prevDislikes + 1;
      updateCommentDislikes(commentId);
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