import { useState } from 'react';
import axios from 'axios';
import Editor from 'components/global/questionItem/Editor';
import AddTag from 'components/global/tag/AddTag';

const QuestionWrite = () => {
  const [title, setTitle] = useState('');
  const [body, setBody] = useState('');
  const [tags, setTags] = useState('');

  const changeTitle = (e) => {
    setTitle(e.currentTarget.value);
  };
  const submitForm = async () => {
    if (title === '') {
    }
    const {
      data: { questionId },
    } = await axios.post('/api/question/write', {
      title: title,
      body: body,
      tags: tags.join(' '),
    });
    navigate(`/detail/${questionId}`);
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
            onChange={changeTitle}
            className="inputBox"
          />
        </div>
        <div>
          <label className="labelText" htmlFor="body">
            Body
          </label>
          <Editor height="400" value={body} setValue={setBody} />
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
