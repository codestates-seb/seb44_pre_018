// 1. 회원 & 비회원 구분없이 조회 가능하기
// 2. 질문 & 답변 불러오기(GET)
// 3. 답변 추가하기 (POST)
// 4. 질문 삭제 기능 구현하기
// 5. 회원 확인 후 답변 생성하기 (아이디와 시간 출력?/ 답변 채택?)
// 6. 해당 질문 작성자와 답변 작성자만 답변 삭제하기(권한 확인 필요)
// 7. 해당 답변 작성자만 답변 수정하기(권한 확인 필요)
// 8. 회원 확인 후 추천 비추천 투표 가능하게 하기
// 9. 무한 스크롤 기능 구현하기 (로드 할 데이터 없을 시 멈추기?)
// 10. 답변 채택 기능 구현하기

import { useState, useEffect } from 'react';
import ItemView from 'components/global/questionItem/ItemView';
import ItemAnswer from 'components/global/questionItem/ItemAnswer';
import TagList from 'components/global/tag/TagList';
import AnswerItem from 'components/global/answerdetail/AnswerItem';
import Editor from 'components/global/questionItem/Editor';

const DetailPage = () => {
  // 조회수 관리를 위한 상태 변수
  const [, setViewCount] = useState(0);
  // 댓글 목록 관리를 위한 상태 변수
  const [answerList, setAnswerList] = useState([]);
  // 댓글 입력값을 관리를 위한 상태 변수
  const [commentInput, setCommentInput] = useState('');

  // 조회수 변경시
  const increaseViewCount = () => {
    setViewCount((prevCount) => prevCount + 1);
  };

  // 댓글 입력값 변경시
  const handleCommentChange = (event) => {
    setCommentInput(event.target.value);
  };

  // 새로운 댓글 추가
  const handleAddAnswer = () => {
  const newAnswer = {
    id: Date.now(),
    content: commentInput,
  };
  setAnswerList((prevList) => [...prevList, newAnswer]);
  setCommentInput('');
}
  // 초기 렌더링 시에 조회수 증가
  useEffect(() => {
    increaseViewCount(); 
  }, []);
  return (
    <div className="inner">
      <div>
        <h3 className="maintitle">
          How to generate a key when you know how to check the key?
        </h3>
        <div className="flex mt-5">
          <div className="mr-3">
            <ItemView />
          </div>
          <div className="mx-3">
            <ItemAnswer />
          </div>
          <p className="ml-auto font-light">Asked: 2023. 06. 13.</p>
        </div>
        <div className="border-t-[1px] border-b-[1px] border-black/[.3] border-solid pb-2">
          <p className="text-sm font-light py-2">
            I have a list of bean objects passed into my JSP page, and one of
            them is a comment field. <br />
            This field may contain newlines, and I want to replace them with
            semicolons using JSTL,
            <br />
            so that the field can be displayed in a text input. I have found one
            solution, but it&apos;s not very elegant. <br />
            I&apos;ll post below as a possibility.
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
