import { useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';
import axios from 'axios';
import ItemView from 'components/global/questionItem/ItemView';
import ItemAnswer from 'components/global/questionItem/ItemAnswer';
import TagList from 'components/global/tag/TagList';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCaretUp, faCaretDown } from '@fortawesome/free-solid-svg-icons';
import { styled } from 'styled-components';

const QuesitonButtonWrap = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 15px 0 10px;
  margin-right: 15px;
  button {
    transition: all 0.3s;
    width: 40px;
    height: 40px;
    border: 1px solid #31a8ff;
    border-radius: 100%;
    color: #31a8ff;
    font-size: 20px;
    &:hover {
      border: 1px solid #002075;
      color: #002075;
    }
  }
  p {
    margin: 7px 0;
  }
`;

const QuestionView = ({ question }) => {

  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}.${month}.${day}`;
  };

  return (
    <div>
      <h3 className="maintitle">{question.title}</h3>
      <div className="flex mt-5">
        <div className="mr-3">
          <ItemView viewCount={question.view}/>
        </div>
        <div className="mx-3">
          <ItemAnswer />
        </div>
        <p className="ml-auto font-light">
          Asked: {formatDate(question.createdAt)}
        </p>
      </div>
      <div className="flex justify-between border-t-[1px] border-b-[1px] border-black/[.3] border-solid pb-2 items-center">
        <QuesitonButtonWrap>
          <button>
            <FontAwesomeIcon icon={faCaretUp} />
          </button>
          <p>2</p>
          <button>
            <FontAwesomeIcon icon={faCaretDown} />
          </button>
        </QuesitonButtonWrap>
        <div className="w-full">
          <div className="font-light flex justify-end ml-auto mt-2">
            <button className="mr-2">수정</button>
            <button>삭제</button>
          </div>
          <p className="text-sm font-light py-2 content">{question.content}</p>
          <TagList />
        </div>
      </div>
    </div>
  );
};

export default QuestionView;
