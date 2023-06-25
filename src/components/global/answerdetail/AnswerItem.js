import { useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';
import axios from 'axios';
import Answer from 'components/global/answerdetail/Answer';
import Editor from 'components/global/questionItem/Editor';

const AnswerItem = () => {
  const { id } = useParams();
  const [answers, setAnswers] = useState([]);
  const [commentInput, setCommentInput] = useState('');
  const [bodyChecked, setBodyChecked] = useState(false);
  const [showInputMessage, setShowInputMessage] = useState(false);

  const handleCheckBody = (isChecked) => {
    setBodyChecked(isChecked);
  };

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`v1/comment/${id}?page=0&size=4`, {
          headers: {
            'ngrok-skip-browser-warning': 'true',
          },
        });
        const data = response.data.data;
        setAnswers(data);
      } catch (error) {
        console.log('Error fetching question data:', error);
      }
    };
    fetchData();
  }, [id]);

  const addAnswer = (newAnswer) => {
    setAnswers((prevAnswers) => {
      const updatedAnswers = [...prevAnswers, newAnswer];
      localStorage.setItem('answers', JSON.stringify(updatedAnswers));
      return updatedAnswers;
    });
  };

  const handleDeleteAnswer = (answerId) => {
    const updatedAnswers = answers.filter(
      (answer) => answer.commentId !== answerId
    );
    setAnswers(updatedAnswers);
    localStorage.setItem('answers', JSON.stringify(updatedAnswers));
  };

  const handleEditAnswer = (answerId, editedContent) => {
    const updatedAnswers = answers.map((answer) => {
      if (answer.commentId === answerId) {
        return { ...answer, content: editedContent };
      }
      return answer;
    });
    setAnswers(updatedAnswers);
    localStorage.setItem('answers', JSON.stringify(updatedAnswers));
  };

  const handleCommentChange = (value) => {
    setCommentInput(value);
    setShowInputMessage(value.trim() === '');
  };

  const handleSubmitAnswer = () => {
    if (commentInput.trim() === '') {
      setShowInputMessage(true);
      return;
    }
    const newAnswer = {
      commentId: Date.now().toString(),
      memberId: '123',
      questionId: id,
      questionTitle: '',
      authenticatedMemberName: 'Username',
      content: commentInput,
      choose: false,
      likeCount: 0,
      dislikeCount: 0,
      commentStatus: 'COMMENT',
    };
    addAnswer(newAnswer);
    setCommentInput('');
  };

  return (
    <>
      {answers.map((answer) => (
        <Answer
          key={answer.commentId}
          answer={answer}
          onDeleteAnswer={handleDeleteAnswer}
          onEditAnswer={handleEditAnswer}
        />
      ))}
      <div className="mt-10">
        <h2 className="mb-4">Your Answer</h2>
        <div>
          <Editor
            height={200}
            value={commentInput}
            setValue={handleCommentChange}
            checkBody={handleCheckBody}
          />
          {showInputMessage && (
            <p style={{ color: 'red' }}>내용을 입력해주세요.</p>
          )}
          <button
            className="pointBu03 my-5"
            type="submit"
            onClick={handleSubmitAnswer}
          >
            Submit your Answer
          </button>
        </div>
      </div>
    </>
  );
};

export default AnswerItem;