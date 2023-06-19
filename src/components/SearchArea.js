import Filter from 'components/Filter';
import SearchBar from 'components/SearchBar';

const SearchArea = ({
  filterValue,
  setFilterValue,
  queryValue,
  setQueryValue,
}) => {
  return (
    <div className="flex items-center justify-between">
      <Filter filterValue={filterValue} setFilterValue={setFilterValue} />
      <SearchBar queryValue={queryValue} setQueryValue={setQueryValue} />
    </div>
  );
};

export default SearchArea;
