import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';

const SearchBar = () => {
  return (
    <div className="relative">
      <input
        type="text"
        className="border border-gray-400 border-solid rounded-3xl w-64 h-9 pl-3 shadow-md active:border-pointCol02 focus-visible:border-pointCol02"
      />
      <FontAwesomeIcon
        icon={faMagnifyingGlass}
        className="absolute right-3 top-1/2 -translate-y-1/2 text-pointCol02"
      />
    </div>
  );
};

export default SearchBar;
