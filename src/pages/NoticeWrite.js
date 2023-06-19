import Editor from 'components/global/questionItem/Editor';
import Tag from 'components/global/tag/AddTag';

const NoticeWrite = () => {
  return (
    <div className="inner">
      <h3 className="maintitle">Ask a public question</h3>
      <div>
        <div>
          <label className="labelText" htmlFor="title">
            Title
          </label>
          <input type="text" id="title" className="inputBox" />
        </div>
        <div>
          <label className="labelText" htmlFor="body">
            Body
          </label>
          <Editor height="400" />
        </div>
        <div>
          <label className="labelText" htmlFor="tag">
            Tag
          </label>
          <Tag />
        </div>
        <button type="submit" className="pointBu03 my-12">
          Post your question
        </button>
      </div>
    </div>
  );
};

export default NoticeWrite;
