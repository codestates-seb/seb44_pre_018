import dynamic from 'next/dynamic';
import React, { useState, useRef, forwardRef } from 'react';
import 'react-quill/dist/quill.snow.css';
import styled from 'styled-components';
const QuillWrapper = dynamic(async () => await import('react-quill'), {
  ssr: false,
  loading: () => <p>Loading ...</p>,
});
const QuillEditor = styled(QuillWrapper)`
  background: #fff;
  height: ${({ height }) => `${height}px`};
  .ql-container {
    height: ${({ height }) => `${height - 41}px`};
  }
`;
const ForwardedQuillEditor = forwardRef((props, ref) => (
  <QuillEditor {...props} innerRef={ref} />
));
function Editor({ height, value, setValue }) {
  const editorRef = useRef(null);

  const handleChange = (content, delta, source, editor) => {
    setValue(content);
  };
  const modules = {
    toolbar: {
      container: [
        [{ header: [3, false] }],
        ['bold', 'italic', 'underline', 'strike', 'blockquote'],
        [{ color: [] }, { background: [] }],
        [
          { list: 'ordered' },
          { list: 'bullet' },
          { indent: '-1' },
          { indent: '+1' },
        ],
        ['link', 'image'],
        ['clean'],
      ],
      size: ['small', 'large'],
    },
  };

  const formats = [
    'header',
    'bold',
    'italic',
    'underline',
    'strike',
    'blockquote',
    'list',
    'bullet',
    'indent',
    'link',
    'image',
    'color',
    'background',
  ];

  return (
    <ForwardedQuillEditor
      ref={editorRef}
      value={value}
      onChange={handleChange}
      height={height}
      modules={modules}
      formats={formats}
      placeholder="내용을 입력해주세요."
    />
  );
}

export default Editor;
