import { getSearch } from 'assets/js/common';
import { useNavigate } from 'react-router';

const Filter = () => {
  const searchData = getSearch();
  const navigate = useNavigate();
  const btns = [
    { value: 'latest', txt: '최신순' },
    { value: 'view', txt: '조회순' },
  ];
  const clickBtn = (e) => {
    const {
      currentTarget: { value },
    } = e;
    const searchObj = getSearch();
    const newObj = { ...searchObj, sortBy: value };

    const searchParams = new URLSearchParams(newObj).toString();
    navigate({
      pathname: location.pathname,
      search: searchParams,
    });
  };
  return (
    <ul className="flex my-8">
      {btns.map((btn) => {
        return (
          <li
            key={btn.value}
            className={`pointBu01 text-xs px-4 py-2 mr-2 sm:px-1.5 sm:py-1  sm:text-xxs sm:mr-1 ${
              searchData.sortBy === btn.value ? 'pointBu02' : ''
            }`}
          >
            <button onClick={clickBtn} value={btn.value}>
              {btn.txt}
            </button>
          </li>
        );
      })}
    </ul>
  );
};

export default Filter;
