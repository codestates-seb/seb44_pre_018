import { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import axios from 'axios';
import Editor from 'components/global/questionItem/Editor';
import AddTag from 'components/global/tag/AddTag';

const QuestionUpdate = () => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [title, setTitle] = useState('');
  const [body, setBody] = useState('');
  const [tags, setTags] = useState('');
  const [checkTitle, setCheckTitle] = useState(false);
  const [checkBody, setCheckBody] = useState(false);
  const [checkTag, setCheckTag] = useState(false);
  const [questionData, setQuestionData] = useState({
    title: '',
    content: '',
  });


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
  
    if (title === '' || body === '') {
      return;
    }
    const resp = await (await axios.get(`/question/${id}`)).data;
    setQuestionData(resp.data);
    await axios.patch(`question/update/${id}/${3}`, questionData)
    .then((res) => {
      alert('수정되었습니다.');
      navigate('-1');
    });
  };

  useEffect(() => {
  const fetchData = async () => {
    try {
      const resp = await axios.get(`/question/${id}`);
      if (resp.data && resp.data.title) {
        const { title, content } = resp.data;
        setTitle(title);
        setBody(content);
  }else {
   console.log('error');
  } } catch (error) {
    console.log('error');
  }
};

  fetchData();
}, [id]);

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
          {checkTag && <p className="notice">태그는 필수 입력해야 합니다.</p>}
        </div>
        <button type="submit" className="pointBu03 my-12" onClick={submitForm}>
          Post your question
        </button>
      </div>
    </div>
  );
};

export default QuestionUpdate;