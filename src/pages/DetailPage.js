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
import { useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';
import axios from 'axios';
import QuestionView from 'components/global/questionItem/QuestionView';
import AnswerItem from 'components/global/answerdetail/AnswerItem';

const DetailPage = () => {
  const { id } = useParams();
  const [question, setQuestion] = useState({
    title: '',
    content: '',
    view: '',
    createdAt: '',
  });
  const [updatedQuestion, setUpdatedQuestion] = useState(null);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`/question/${id}`, {
          headers: {
            'ngrok-skip-browser-warning': 'true',
          },
        });
        const data = response.data.data;
        setQuestion({
          title: data.title,
          content: data.content,
          view: data.view,
          createdAt: data.createdAt,
        });
      } catch (error) {
        console.log('Error fetching question data:', error);
      }
    };

    fetchData();
  }, [id]);


  return (
    <div className="inner">
       {updatedQuestion ? (
        <QuestionView question={updatedQuestion} />
      ) : (
        <QuestionView question={question} />
      )}
      {/* 댓글 */}
      <AnswerItem />
    </div>
  );
};

export default DetailPage;