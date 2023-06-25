import { getSearch } from 'assets/js/common';
import { useNavigate } from 'react-router';
import Filter from 'components/Filter';
import SearchBar from 'components/SearchBar';

const SearchArea = () => {
  const navigate = useNavigate();
  const clickBtn = (txt) => {
    const value = txt;
    const searchObj = getSearch();
    const newObj = { ...searchObj, keyword: value };
    const searchParams = new URLSearchParams(newObj).toString();
    navigate({
      pathname: location.pathname,
      search: searchParams,
    });
  };

  return (
    <div className="flex items-center justify-between">
      <Filter />
      <SearchBar clickBtn={clickBtn} />
    </div>
  );
};

export default SearchArea;
