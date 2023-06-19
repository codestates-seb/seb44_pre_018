import { useState } from 'react';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import styled from 'styled-components';

const TagWrap = styled.span`
  button {
    color: #fff;
  }
  &:hover {
    background-color: #002075;
    button {
      rotate: 30deg;
      transition: all 0.2s;
    }
  }
`;
const HashName = styled.h3`
  color: #fff;
  margin-right: 5px;
`;

const InputBox = styled.input`
  border: none;
  font-size: 12px;
  &:focus {
    outline: none;
  }
`;

const Tag = () => {
  const [tags, setTags] = useState([]);
  const [tag, setTag] = useState('');
  const removeTag = (i) => {
    const clonetags = tags.slice();
    clonetags.splice(i, 1);
    setTags(clonetags);
  };
  const addTag = (e) => {
    setTag(e.target.value);
  };
  const handleKeyPress = (e) => {
    if (e.key === 'Enter') {
      handleClick();
    }
  };
  const handleClick = () => {
    setTags([...tags, tag]);
    setTag('');
  };

  return (
    <div className="inputBox flex h-auto py-1">
      {tags.map((e, i) => (
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

      <InputBox
        placeholder="Press enter to add tags"
        onChange={(e) => addTag(e)}
        onKeyPress={(e) => handleKeyPress(e)}
        value={tag}
      />
    </div>
  );
};

export default Tag;
