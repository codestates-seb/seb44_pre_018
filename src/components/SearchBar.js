import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';

const SearchBar = (props) => {
  const [search, setSearch] = useState('');
  const onChange = (e) => {
    setSearch(e.target.value.toLowerCase());
    props.propFunction(search);
  };
  return (
    <div className="relative w-64">
      <input
        type="text"
        value={search}
        onChange={onChange}
        className="border border-gray-400 border-solid rounded-3xl w-full h-9 pl-3 shadow-md active:border-pointCol02 focus-visible:border-pointCol02"
      />
      <FontAwesomeIcon
        icon={faMagnifyingGlass}
        className="absolute right-3 top-1/2 -translate-y-1/2 text-pointCol02"
      />
    </div>
  );
};

export default SearchBar;
