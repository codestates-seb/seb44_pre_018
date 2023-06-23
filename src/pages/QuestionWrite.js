import { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import axios from 'axios';
import Editor from 'components/global/questionItem/Editor';
import AddTag from 'components/global/tag/AddTag';

const QuestionWrite = () => {
  const navigate = useNavigate();
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
  const tagChange = (e) => {
    setCheckTag(false);
  };
  const submitForm = async () => {
    setCheckTitle(title === '');
    setCheckBody(body === '');
    //setCheckTag(tags === '');
    try {
      const result = await axios.post('/question/1/create', {
        title: title,
        content: body,
      });
      navigate(`/detail/1`);
    } catch (err) {
      console.log(err);
    }
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
          <AddTag tags={tags} setTags={setTags} checkTag={setCheckTag} />
          {checkTag && <p className="notice">태그는 필수 입력해야 합니다.</p>}
        </div>
        <button type="submit" className="pointBu03 my-12" onClick={submitForm}>
          Post your question
        </button>
      </div>
    </div>
  );
};

export default QuestionWrite;
