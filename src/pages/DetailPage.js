// 1. 회원 & 비회원 구분없이 조회 가능하기
// 2. 질문 & 답변 불러오기(GET)
// 3. 답변 추가하기 (POST)
// 4. 질문 삭제 기능 구현하기(작성자만 삭제 가능)
// 5. 회원 확인 후 답변 생성하기 (아이디와 시간 출력?/ 답변 채택?)
// 6. 해당 질문 작성자와 답변 작성자만 답변 삭제하기(권한 확인 필요)
// 7. 해당 답변 작성자만 답변 수정하기(권한 확인 필요)
// 8. 회원 확인 후 추천 비추천 투표 가능하게 하기
// 9. 무한 스크롤 기능 구현하기 (로드 할 데이터 없을 시 멈추기?)
// 10. 답변 채택 기능 구현하기

import { useState, useEffect } from 'react';
import axios from 'axios';
import ItemView from 'components/global/questionItem/ItemView';
import ItemAnswer from 'components/global/questionItem/ItemAnswer';
import TagList from 'components/global/tag/TagList';
import AnswerItem from 'components/global/answerdetail/AnswerItem';
import Editor from 'components/global/questionItem/Editor';

const DetailPage = () => {
  // 질문 관리를 위한 상태 변수
  const [question, setQuestion] = useState({});

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get('/data/data.json');
        const data = response.data;
        if (data && data.questions && data.questions.length > 0) {
          const question = data.questions[0];
          setQuestion(question);
        }
      } catch (error) {
        console.log('Error fetching question data:', error);
      }
    };

    fetchData();
  }, []);
  
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}.${month}.${day}`;
  };
  
  return (
    <div className="inner">
      <div>
        <h3 className="maintitle">
        {question.title}
        </h3>
        <div className="flex mt-5">
          <div className="mr-3">
            <ItemView />
          </div>
          <div className="mx-3">
            <ItemAnswer />
          </div>
          <p className="ml-auto font-light">{formatDate(question.createdAt)}</p>
        </div>
        <div className="border-t-[1px] border-b-[1px] border-black/[.3] border-solid pb-2">
          <p className="text-sm font-light py-2 content">
          {question.content}
          </p>
          <TagList/>
        </div>
      </div>

      {/* 댓글 */}
      <AnswerItem/>
    </div>
  );
};

export default DetailPage;
