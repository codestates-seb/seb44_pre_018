import { useRef, useState, useMemo } from 'react';
import ReactQuill from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import styled from 'styled-components';

const QuillEditor = styled(ReactQuill)`
  background: #fff;
  height: ${({ height }) => `${height}px`};
  .ql-container {
    height: ${({ height }) => `${height - 41}px`};
  }
  @media (max-width: 981px) {
    margin-bottom: 40px;
  }
`;

const Editor = ({ height, value, setValue, checkBody }) => {
  // Destructure the height and onChange props
  const editorRef = useRef(null);

  const modules = useMemo(
    () => ({
      toolbar: {
        container: [
          ['bold', 'italic', 'underline', 'strike', 'blockquote'],
          [{ size: ['small', false, 'large', 'huge'] }, { color: [] }],
          [
            { list: 'ordered' },
            { list: 'bullet' },
            { indent: '-1' },
            { indent: '+1' },
            { align: [] },
          ],
          ['link', 'image'],
          ['clean'],
        ],
        size: ['small', 'large'],
      },
    }),
    []
  );

  const handleContentChange = (content) => {
    setValue(content);
    checkBody(false);
  };

  return (
    <>
      <QuillEditor
        ref={editorRef}
        value={value}
        onChange={handleContentChange}
        height={height}
        modules={modules}
        theme="snow"
        placeholder="내용을 입력해주세요."
      />
    </>
  );
};

export default Editor;
