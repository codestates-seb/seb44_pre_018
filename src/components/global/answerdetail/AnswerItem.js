import { useState, useEffect } from 'react';
import axios from 'axios';
import Answer from 'components/global/answerdetail/Answer';
import Editor from 'components/global/questionItem/Editor';

const AnswerItem = ({ answerList, onDeleteAnswer, onEditAnswer }) => {
  const [answers, setAnswers] = useState([]);
  const [commentInput, setCommentInput] = useState("");

  const getAnswerData = async () => {
    try {
      // 서버에서 데이터 가져오기
      const result = await axios.get('/data/answers.json');
      const answers = result.data.answers;
      // 가져온 데이터를 localStorage에 저장
      localStorage.setItem('answers', JSON.stringify(answers));
      // 상태 업데이트
      setAnswers(answers);
    } catch (err) {
      console.log('Error getting answer data:', err);
    }
  };
  

  const addAnswer = (newAnswer) => {
    setAnswers((prevAnswers) => {
      const updatedAnswers = [...prevAnswers, newAnswer];
      localStorage.setItem('answers', JSON.stringify(updatedAnswers));
      return updatedAnswers;
    });
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
    // localStorage에서 데이터 확인
    const storedAnswers = localStorage.getItem('answers');
    if (storedAnswers) {
      setAnswers(JSON.parse(storedAnswers));
    } else {
      // localStorage에 데이터가 없으면 서버에서 가져오기
      getAnswerData();
    }
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
          <Editor height={200} value={commentInput} onChange={setCommentInput} />
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