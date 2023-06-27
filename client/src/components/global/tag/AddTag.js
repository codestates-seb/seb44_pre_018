import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import styled from 'styled-components';

const TagWrap = styled.span`
  button {
    color: #fff;
    transition: all 0.2s;
  }
  &:hover {
    background-color: #002075;
    button {
      rotate: 90deg;
    }
  }
`;
const HashName = styled.h3`
  color: #fff;
  margin-right: 5px;
`;

const TagInput = styled.input`
  border: none;
  font-size: 12px;
  &:focus {
    outline: none;
  }
`;

const AddTag = ({ tags, setTags, checkTag }) => {
  const [tagArray, setTagArray] = useState([]);
  const [tag, setTag] = useState('');
  const removeTag = (i) => {
    const clonetags = tagArray.slice();
    clonetags.splice(i, 1);
    setTagArray(clonetags);
  };
  const changeTag = (e) => {
    setTag(e.target.value);
  };
  const handleKeyPress = (e) => {
    if (e.key === 'Enter' || e.code == 'Space') {
      checkTag(false);
      handleClick();
    }
  };
  const handleClick = () => {
    setTagArray([...tagArray, tag]);
    setTag('');
  };

  return (
    <div className="inputBox flex h-auto py-1 items-center flex-wrap">
      {tagArray.map((e, i) => (
        <TagWrap
          className="pointBu03 text-xxs px-2 py-1 mr-1 font-extralight h-6 "
          key={i}
        >
          <HashName>{e}</HashName>
          <button onClick={() => removeTag(i)}>
            <FontAwesomeIcon icon={faXmark} />
          </button>
        </TagWrap>
      ))}

      <TagInput
        placeholder="Press enter to add tags"
        onChange={changeTag}
        onKeyPress={handleKeyPress}
        value={tag}
      />
    </div>
  );
};

export default AddTag;
