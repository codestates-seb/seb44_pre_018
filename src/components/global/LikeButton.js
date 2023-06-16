import { useState, useEffect } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faThumbsUp, faThumbsDown } from '@fortawesome/free-solid-svg-icons';
import styled from 'styled-components';

const LikesContainer = styled.div`
  display: flex;
  align-items: center;
  margin-right: 1rem;
  border: 2px solid #002075;
  color: #002075;
  padding: 0.5rem;
  cursor: pointer;

  span {
    margin-left: 0.5rem;
  }
`;

const LikeButton = () => {
  const [likes, setLikes] = useState(() => {
    const storedLikes = localStorage.getItem('likes');
    return storedLikes ? Number(storedLikes) : 0;
  });

  const [dislikes, setDislikes] = useState(() => {
    const storedDislikes = localStorage.getItem('dislikes');
    return storedDislikes ? Number(storedDislikes) : 0;
  });

  const handleLikeClick = () => {
    setLikes((prevLikes) => {
      const newLikes = prevLikes + 1;
      localStorage.setItem('likes', newLikes);
      return newLikes;
    });
  };

  const handleDislikeClick = () => {
    setDislikes((prevDislikes) => {
      const newDislikes = prevDislikes + 1;
      localStorage.setItem('dislikes', newDislikes);
      return newDislikes;
    });
  };

  useEffect(() => {
    const handleStorageChange = (event) => {
      if (event.key === 'likes') {
        setLikes(Number(event.newValue));
      } else if (event.key === 'dislikes') {
        setDislikes(Number(event.newValue));
      }
    };

    window.addEventListener('storage', handleStorageChange);

    return () => {
      window.removeEventListener('storage', handleStorageChange);
    };
  }, []);

  return (
    <>
      <LikesContainer onClick={handleLikeClick}>
        <FontAwesomeIcon icon={faThumbsUp} />
        <span>{likes}</span>
      </LikesContainer>
      <LikesContainer onClick={handleDislikeClick}>
        <FontAwesomeIcon icon={faThumbsDown} />
        <span>{dislikes}</span>
      </LikesContainer>
    </>
  );
};

export default LikeButton;
