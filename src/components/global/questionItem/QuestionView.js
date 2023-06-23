import { useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';
import axios from 'axios';
import ItemView from 'components/global/questionItem/ItemView';
import ItemAnswer from 'components/global/questionItem/ItemAnswer';
import TagList from 'components/global/tag/TagList';

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
          <ItemView />
        </div>
        <div className="mx-3">
          <ItemAnswer />
        </div>
        <p className="ml-auto font-light">
          Asked: {formatDate(question.createdAt)}
        </p>
      </div>
      <div className="border-t-[1px] border-b-[1px] border-black/[.3] border-solid pb-2 items-center">
        <div className="font-light flex justify-end ml-auto mt-2">
          <button className="mr-2">수정</button>
          <button>삭제</button>
        </div>
        <p className="text-sm font-light py-2 content">{question.content}</p>
        <TagList />
      </div>
    </div>
  );
};

export default QuestionView;