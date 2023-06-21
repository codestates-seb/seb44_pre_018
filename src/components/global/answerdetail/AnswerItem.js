import { useState, useEffect } from 'react';
import axios from 'axios';
import Answer from 'components/global/answerdetail/Answer';
import Editor from 'components/global/questionItem/Editor';

const AnswerItem = () => {
  const [answers, setAnswers] = useState([]);
  const [commentInput, setCommentInput] = useState('');
  const [bodyChecked, setBodyChecked] = useState(false);

  const handleCheckBody = (isChecked) => {
    setBodyChecked(isChecked);
  };

  const getAnswerData = async () => {
    try {
      const storedAnswers = localStorage.getItem('answers');
      if (storedAnswers) {
        const answers = JSON.parse(storedAnswers);
        setAnswers(answers);
      } else {
        const result = await axios.get('/data/answers.json');
        const answers = result.data.answers.map((answer) => ({
          ...answer,
          key: answer.answerId,
        }));
        localStorage.setItem('answers', JSON.stringify(answers));
        setAnswers(answers);
      }
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

  const handleDeleteAnswer = (answerId) => {
    const updatedAnswers = answers.filter((answer) => answer.answerId !== answerId);
    setAnswers(updatedAnswers);
    localStorage.setItem('answers', JSON.stringify(updatedAnswers));
  };

  const handleEditAnswer = (answerId, editedContent) => {
    const updatedAnswers = answers.map((answer) => {
      if (answer.answerId === answerId) {
        return { ...answer, content: editedContent };
      }
      return answer;
    });
    setAnswers(updatedAnswers);
    localStorage.setItem('answers', JSON.stringify(updatedAnswers));
  };

  const handleCommentChange = (value) => {
    setCommentInput(value);
  };

  const handleSubmitAnswer = () => {
    if (commentInput.trim() === '') {
      return;
    }
    const newAnswer = {
      questionId: '1',
      answerId: Date.now().toString(),
      content: commentInput,
    };
    addAnswer(newAnswer);
    setCommentInput('');
  };

  useEffect(() => {
    const storedAnswers = localStorage.getItem('answers');
    if (storedAnswers) {
      setAnswers(JSON.parse(storedAnswers));
      getAnswerData();
    } else {
      getAnswerData();
    }
  }, []);

  useEffect(() => {
    localStorage.setItem('answers', JSON.stringify(answers));
  }, [answers]);

  return (
    <>
    {answers.map((answer, index) => (
        <Answer 
        key={`${answer.answerId}-${index}`} 
        answer={answer} 
        onDeleteAnswer={handleDeleteAnswer}
        onEditAnswer={handleEditAnswer}/>
      ))}
      <div className="mt-10">
        <h2>Your Answer</h2>
        <div>
          <Editor
            height={200}
            value={commentInput}
            setValue={handleCommentChange}
            checkBody={handleCheckBody}
          />
          <button className="pointBu03" type="submit" onClick={handleSubmitAnswer}>
            Submit your Answer
          </button>
        </div>
      </div>
    </>
  );
};

export default AnswerItem;