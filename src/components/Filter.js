const Filter = ({ filterValue, setFilterValue }) => {
  const clickBtn = (e) => {
    setFilterValue(e.currentTarget.value);
    console.log('vvv');
  };
  return (
    <ul className="flex my-8">
      <li className="on pointBu01 text-xs px-4 py-2 mr-2 px-1.5 py-1  text-xxs mr-1">
        <button onClick={clickBtn} value="latest">
          최신순
        </button>
      </li>
      <li className="pointBu01 text-xs px-4 py-2 mr-2 px-1.5 py-1  text-xxs mr-1">
        <button onClick={clickBtn} value="popular">
          조회 순
        </button>
      </li>
      <li className="pointBu01 text-xs px-4 py-2 px-1.5 py-1  text-xxs mr-1">
        <button onClick={clickBtn} value="answer">
          최근 답변 달린 순
        </button>
      </li>
    </ul>
  );
};

export default Filter;
