import { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faThumbsUp, faThumbsDown } from '@fortawesome/free-solid-svg-icons';
import styled from 'styled-components';
import LoginModal from 'components/global/login/LoginModal';


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

const VoteButton = ({ commentId, likeCount, dislikeCount }) => {
  const [likes, setLikes] = useState(likeCount);
  const [dislikes, setDislikes] = useState(dislikeCount);
  const [showModal, setShowModal] = useState(false);
  const { user } = useSelector((state) => state);

  const updateCommentLikes = async (commentId) => {
    try {
      await axios.patch(`/comment/like/${commentId}`,{}, {
          headers: {
            Authorization: user.token,
          },
    }
    );
    } catch (error) {
      console.error(error);
    }
  };
  
  const updateCommentDislikes = async (commentId) => {
    try {
      await axios.patch(`/comment/dislike/${commentId}`,{}, {
        headers: {
          Authorization: user.token,
        },
  });
    } catch (error) {
      console.error(error);
    }
  };

  const handleLikeClick = () => {
    if(user.token) {
    setLikes((prevLikes) => {
      const newLikes = prevLikes + 1;
      updateCommentLikes(commentId);
      return newLikes;
    });
  } else {
    setShowModal(true);
  }
  };

  const handleDislikeClick = () => {
    if (user.token) {
    setDislikes((prevDislikes) => {
      const newDislikes = prevDislikes + 1;
      updateCommentDislikes(commentId);
      return newDislikes;
    });
  } else {
    setShowModal(true);
  }
  };

  const handleModalClose = () => {
    setShowModal(false);
  };

  const handleModalCancel = () => {
    setShowModal(false);
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
      {showModal && (
          <LoginModal
            onClose={handleModalClose}
            onCancel={handleModalCancel}
            isOpen={showModal}
          />
        )}
    </>
  );
};

export default VoteButton;
