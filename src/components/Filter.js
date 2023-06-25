const Filter = ({ filterValue, setFilterValue }) => {
  const clickBtn = (e) => {
    setFilterValue(e.currentTarget.value);
    console.log('vvv');
  };
  return (
    <ul className="flex my-8">
      <li className="on pointBu01 text-xs px-4 py-2 mr-2 sm:px-1.5 sm:py-1  sm:text-xxs sm:mr-1">
        <button onClick={clickBtn} value="latest">
          최신순
        </button>
      </li>
      <li className="pointBu01 text-xs px-4 py-2 mr-2 sm:px-1.5 sm:py-1  sm:text-xxs sm:mr-1">
        <button onClick={clickBtn} value="popular">
          조회 순
        </button>
      </li>
    </ul>
  );
};

export default Filter;
