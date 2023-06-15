import Editor from '../components/global/Editor';

const NoticeWrite = () => {
  return (
    <div className="inner">
      <h3 className="maintitle">Ask a public question</h3>
      <form>
        <div>
          <label htmlFor="title">Title</label>
          <input type="text" id="title" className="inputBox" />
        </div>
        <div>
          <label htmlFor="body">Body</label>
          <Editor />
        </div>
        <div>
          <label htmlFor="tag">Tag</label>
          <input type="text" id="tag" className="inputBox" />
        </div>
      </form>
    </div>
  );
};

export default NoticeWrite;
