/* eslint-disable import/no-unresolved */
// 1. 회원 & 비회원 구분없이 조회 가능하기
// 2. 질문 & 답변 불러오기(GET)
// 3. 답변 추가하기 (POST)
// 4. 질문 삭제 기능 구현하기
// 5. 회원 확인 후 답변 생성하기 (아이디와 시간 출력?/ 답변 채택?)
// 6. 해당 질문 작성자와 답변 작성자만 답변 삭제하기(권한 확인 필요)
// 7. 해당 답변 작성자만 답변 수정하기(권한 확인 필요)
// 8. 회원 확인 후 추천 비추천 투표 가능하게 하기
// 9. 무한 스크롤 기능 구현하기 (로드 할 데이터 없을 시 멈추기?)

import { useState, useEffect } from 'react';
import ItemView from '../components/global/ItemView';
import ItemAnswer from '../components/global/ItemAnswer';
import CKEditor from '@ckeditor/ckeditor5-react';
import ClassicEditor from '@ckeditor/ckeditor5-build-classic';

const DetailPage = () => {
  const [, setViewCount] = useState(0);
  const [content, setContent] = useState('');

  useEffect(() => {
    increaseViewCount(); // 초기 렌더링 시에 조회수 증가
  }, []);

  const increaseViewCount = () => {
    setViewCount((prevCount) => prevCount + 1);
  };

  const handleContentChange = (event, editor) => {
    const data = editor.getData();
    setContent(data);
  };

  return (
    <div className="inner">
      <div className="flex items-center justify-between">
        <h3 className="maintitle">
          How to generate a key when you know how to check the key?
        </h3>
        <div className="flex items-center justify-between">
          <ItemView />
          <ItemAnswer />
          <p>Asked: 2023. 06. 13.</p>
        </div>
        <p>
          I am trying to use math kernel library of intel along with the intel
          fortran compiler, which comes built into the oneAPI basekit. However,
          I am not able to use ifort since the terminal complains ...
        </p>
        <p>태그: 태그</p>
      </div>
      {/* 댓글 구조 */}
      <h2>댓글</h2>
      <div>
        <p>I have a list of bean objects passed into my JSP page.</p>
        <p>작성자: 냥냥씨 </p>
        <p>작성일: 2023. 06. 13.</p>
        <button>추천</button>
        <button>비추천</button>

        {/* hover 시 */}
        <div>
          <button>수정</button>
          <button>삭제</button>
        </div>
      </div>

      {/* 답변 폼 */}
      <div>
        <h2>Your Answer</h2>
        <form>
          <button type="submit">Edit your Answer</button>
        </form>
        <CKEditor
          editor={ClassicEditor}
          data={content}
          onChange={handleContentChange}
        />
      </div>
    </div>
  );
};

export default DetailPage;
