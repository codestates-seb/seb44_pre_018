import { useState, useEffect } from 'react';
import axios from 'axios';
import Answer from 'components/global/answerdetail/Answer';
import Editor from 'components/global/questionItem/Editor';

const AnswerItem = ({ answerList, onDeleteAnswer, onEditAnswer }) => {
  const [answers, setAnswers] = useState([]);
  const [commentInput, setCommentInput] = useState("");

  const getAnswerData = async () => {
    try {
      const result = await axios.get('/data/answers.json');
      setAnswers(result.data.answers);
    } catch (err) {
      console.log('Error getting answer data:', err);
    }
  };

  const addAnswer = (newAnswer) => {
    setAnswers((prevAnswers) => [...prevAnswers, newAnswer]);
  };

  const editAnswer = (answerId, content) => {
    setAnswers((prevAnswers) =>
      prevAnswers.map((answer) =>
        answer.answerId === answerId ? { ...answer, content: content } : answer
      )
    );
  };

  const handleCommentChange = (value) => {
    setCommentInput(value);
  };

  const handleSubmitAnswer = () => {
    const newAnswer = { questionId: '1', answerId: '3', content: commentInput };
    addAnswer(newAnswer);
    setCommentInput("");
  };

  useEffect(() => {
    getAnswerData();
  }, []);

  return (
    <>
      {answers.map((answer) => (
        <Answer
          key={answer.answerId}
          answer={answer}
          onDeleteAnswer={onDeleteAnswer}
          onEditAnswer={onEditAnswer}
        />
      ))}
      <div className="mt-10">
        <h2>Your Answer</h2>
        <div>
          <Editor height="200" value={commentInput} onChange={handleCommentChange} />
          <button
            className="pointBu03"
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