import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';

const SearchBar = ({ queryValue, setQueryValue }) => {
  const [thisText, setThisText] = useState(queryValue);
  const onChangeThis = (e) => {
    setThisText(e.currentTarget.value);
  };
  const submitQuery = () => {
    setQueryValue(thisText);
    console.log('aaa');
  };
  return (
    <div className="relative w-64">
      <input
        type="text"
        value={thisText}
        onChange={onChangeThis}
        className="border border-gray-400 border-solid rounded-3xl w-full h-9 pl-3 shadow-md active:border-pointCol02 focus-visible:border-pointCol02"
      />
      <button
        className="absolute right-3 top-1/2 -translate-y-1/2 text-pointCol02"
        onClick={submitQuery}
      >
        <FontAwesomeIcon icon={faMagnifyingGlass} />
      </button>
    </div>
  );
};

export default SearchBar;
