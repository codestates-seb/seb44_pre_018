import { useParams, useNavigate } from 'react-router-dom';
import { useState, useEffect } from 'react';
import axios from 'axios';
import Answer from 'components/global/answerdetail/Answer';
import Editor from 'components/global/questionItem/Editor';
import LoginModal from 'components/global/login/LoginModal';

const AnswerItem = ({ itemid }) => {
  const { id } = useParams();
  const navigate = useNavigate();
  const [answers, setAnswers] = useState([]);
  const [commentInput, setCommentInput] = useState('');
  const [bodyChecked, setBodyChecked] = useState(false);
  const [showInputMessage, setShowInputMessage] = useState(false);
  const [loggedIn, setLoggedIn] = useState(false);
  const [showModal, setShowModal] = useState(false);

  const handleCheckBody = (isChecked) => {
    setBodyChecked(isChecked);
  };

  const getCommentList = async () => {
    try {
      const response = await axios.get(`/v1/comment/${id}`, {
        headers: {
          'ngrok-skip-browser-warning': 'true',
        },
        params: {
          page: 0,
          size: 4,
        },
      });
      setAnswers(response.data.data);
    } catch (error) {
      console.error(error);
    }
  };

  const addComment = async (content) => {
    try {
      const response = await axios.post(
        `/v1/comment/${id}/${2}/question-answer`,
        {
          content: content,
        }
      );
      const newAnswer = response.data.data;
      setAnswers((prevAnswers) => [...prevAnswers, newAnswer]);
      console.log('댓글 추가 성공:', response.data);
    } catch (error) {
      console.error('댓글 추가 실패:', error);
      throw error;
    }
  };

  const handleDeleteAnswer = async (answerId) => {
    try {
      await fetch(`/v1/comment/delete/${id}/1`, {
        method: 'DELETE',
      });

      const updatedAnswers = answers.filter(
        (answer) => answer.commentId !== answerId
      );
      setAnswers(updatedAnswers);
      console.log('댓글 삭제 성공');
    } catch (error) {
      console.error('댓글 삭제 실패:', error);
    }
  };

  const handleEditComment = async (
    questionId,
    commentId,
    memberId,
    editedContent
  ) => {
    try {
      const response = await axios.patch(`/v1/comment/update/${id}/${1}/${1}`, {
        content: editedContent,
      });

      const updatedAnswers = answers.map((answer) => {
        if (answer.commentId === commentId) {
          return { ...answer, content: editedContent };
        }
        return answer;
      });

      setAnswers(updatedAnswers);
      localStorage.setItem('answers', JSON.stringify(updatedAnswers));

      console.log('댓글 수정 성공:', response.data);
    } catch (error) {
      console.error('댓글 수정 실패:', error);
    }
  };

  const handleCommentChange = (value) => {
    setCommentInput(value);
    setShowInputMessage(value.trim() === '');
  };

  const handleSubmitAnswer = async () => {
    if (commentInput.trim() === '') {
      setShowInputMessage(true);
      return;
    }
    if (!loggedIn) {
      setShowModal(true);
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
    try {
      await addComment(newAnswer.content);
      setCommentInput('');
      setShowInputMessage(false);
    } catch (error) {
      console.error(error);
    }
  };

  const handleModalClose = () => {
    setShowModal(false);
    navigate('/login');
  };

  const handleModalCancel = () => {
    setShowModal(false);
  };

  useEffect(() => {
    getCommentList();
    setLoggedIn(false);
    console.log(id);
  }, []);

  return (
    <>
      {answers.map((answer) => (
        <Answer
          key={answer.commentId}
          answer={answer}
          onDeleteAnswer={handleDeleteAnswer}
          onEditAnswer={handleEditComment}
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
        {showModal && (
          <LoginModal
            onClose={handleModalClose}
            onCancel={handleModalCancel}
            isOpen={showModal}
          />
        )}
      </div>
    </>
  );
};

export default AnswerItem;
