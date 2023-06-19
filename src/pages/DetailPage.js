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
import ItemView from '../components/global/ItemView';
import ItemAnswer from '../components/global/ItemAnswer';
import TagList from '../components/global/TagList';
import AnswerItem from '../components/AnswerItem';
import Editor from '../components/global/Editor';

const DetailPage = () => {
  const [, setViewCount] = useState(0);

  useEffect(() => {
    increaseViewCount(); // 초기 렌더링 시에 조회수 증가
  }, []);

  const increaseViewCount = () => {
    setViewCount((prevCount) => prevCount + 1);
  };

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
        <ul className="border-t-[1px] border-black/[.3] border-solid">
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
          <TagList />
        </ul>
      </div>

      {/* 댓글 구조 */}
      <AnswerItem />

      {/* 답변 폼 */}
      <div className="mt-10">
        <h2>Your Answer</h2>
        <div>
          <Editor height="200" />
          <button className="pointBu03" type="submit">
            Edit your Answer
          </button>
        </div>
      </div>
    </div>
  );
};

export default DetailPage;
