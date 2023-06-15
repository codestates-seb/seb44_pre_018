const NoticeWrite = () => {
  return (
    <div className="inner">
      <h3 className="maintitle">Ask a public question</h3>
      <form>
        <div>
          <label htmlFor="title">Title</label>
          <input type="text" id="title" />
        </div>
        <div>
          <label htmlFor="body">Body</label>
          <input type="text" id="body" />
        </div>
        <div>
          <label htmlFor="tag">Tag</label>
          <input type="text" id="tag" />
        </div>
      </form>
    </div>
  );
};

export default NoticeWrite;
