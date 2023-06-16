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
`;

const Editor = (height) => {
  const editorRef = useRef(null);
  const [contents, setContents] = useState('');
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

  return (
    <>
      <QuillEditor
        ref={editorRef}
        value={contents}
        onChange={setContents}
        height={height.height}
        modules={modules}
        theme="snow"
        placeholder="내용을 입력해주세요."
      />
    </>
  );
};
export default Editor;
