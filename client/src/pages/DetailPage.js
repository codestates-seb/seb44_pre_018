import { useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';
import axios from 'axios';
import QuestionView from 'components/global/questionItem/QuestionView';
import AnswerItem from 'components/global/answerdetail/AnswerItem';

const DetailPage = () => {
  const { id } = useParams();
  const [question, setQuestion] = useState({
    title: '',
    content: '',
    view: '',
    createdAt: '',
  });
  const [updatedQuestion, setUpdatedQuestion] = useState(null);

  const [likeCount, setLikeCount] = useState(0);
  const [dislikeCount, setDislikeCount] = useState(0);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const response = await axios.get(`/question/${id}`, {
          headers: {
            'ngrok-skip-browser-warning': 'true',
          },
        });
        const data = response.data.data;
        console.log(data);
        setLikeCount(data.likeCount);
        setDislikeCount(data.dislikeCount);
        setQuestion({
          title: data.title,
          content: data.content,
          view: data.view,
          createdAt: data.createdAt,
        });
      } catch (error) {
        console.log('Error fetching question data:', error);
      }
    };

    fetchData();
  }, [id]);

  return (
    <div className="inner">
      {updatedQuestion ? (
        <QuestionView question={updatedQuestion} likeCount={likeCount} dislikeCount={dislikeCount}/>
      ) : (
        <QuestionView question={question} likeCount={likeCount} dislikeCount={dislikeCount}/>
      )}
      {/* 댓글 */}
      <AnswerItem itemid={id} />
    </div>
  );
};

export default DetailPage;
