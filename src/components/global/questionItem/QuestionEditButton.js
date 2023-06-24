import { useState } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import React from 'react';

const QuestionEditButton = (question) => {
  const [post, setPost] = useState(question);
  const { id } = useParams();
  const navigate = useNavigate();
  
  const moveToUpdate = () => {
      navigate(`/update/${id}`);
    };

    const handleDeleteQuestion = () => {
      if(window.confirm('삭제하시겠습니까?')) {
        fetch(`/question/delete/${id}/${1}`, {
          method: 'DELETE'
        }). then (res => {
          if(res.ok) {
            setPost({id:0});
            navigate('/');
          }
        })
      }
    }

    if( id === 0) {
      return null;
    }

  return (
    <>
      <button className="mr-2" onClick={moveToUpdate}>
        수정
      </button>
      <button onClick={handleDeleteQuestion}>
        삭제
      </button>
    </>
  );
}

export default QuestionEditButton;