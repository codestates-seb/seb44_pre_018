import { useState } from 'react';
import axios from 'axios';
import Editor from 'components/global/questionItem/Editor';
import AddTag from 'components/global/tag/AddTag';

const QuestionWrite = () => {
  const [title, setTitle] = useState('');
  const [body, setBody] = useState('');
  const [tags, setTags] = useState('');
  const [checkTitle, setCheckTitle] = useState(false);
  const [checkBody, setCheckBody] = useState(false);
  const [checkTag, setCheckTag] = useState(false);

  const titleChange = (e) => {
    setCheckTitle(false);
    setTitle(e.currentTarget.value);
  };
  const bodyChange = (e) => {
    setCheckBody(false);
    setBody(e.currentTarget.value);
  };
  const tagChange = (e) => {
    setCheckTag(false);
  };
  const submitForm = async () => {
    // const {
    //   data: { questionId },
    // } = await axios.post('/api/question/write', {
    //   title: title,
    //   body: body,
    //   tags: tags.join(''),
    // });
    //navigate(`/detail/${questionId}`);
    setCheckTitle(title === '');
    setCheckBody(body === '');
    setCheckTag(tags === '');
  };
  return (
    <div className="inner">
      <h3 className="maintitle">Ask a public question</h3>
      <div>
        <div>
          <label className="labelText" htmlFor="title">
            Title
          </label>
          <input
            type="text"
            id="title"
            value={title}
            onChange={titleChange}
            className={`inputBox ${checkTitle && 'disabledInput'}`}
          />
          {checkTitle && <p className="notice">제목은 필수 입력해야 합니다.</p>}
        </div>

        <div>
          <label className="labelText" htmlFor="body">
            Body
          </label>
          <Editor
            height="400"
            value={body}
            setValue={setBody}
            checkBody={setCheckBody}
          />
          {checkBody && <p className="notice">본문은 필수 입력해야 합니다.</p>}
        </div>

        <div>
          <label className="labelText" htmlFor="tag">
            Tag
          </label>
          <AddTag tags={tags} setTags={setTags} />
        </div>
        <button type="submit" className="pointBu03 my-12" onClick={submitForm}>
          Post your question
        </button>
      </div>
    </div>
  );
};

export default QuestionWrite;
