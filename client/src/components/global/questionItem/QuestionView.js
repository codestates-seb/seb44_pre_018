import { useParams } from 'react-router-dom';
import { useState, useEffect } from 'react';
import { useSelector } from 'react-redux';
import axios from 'axios';
import ItemView from 'components/global/questionItem/ItemView';
import ItemAnswer from 'components/global/questionItem/ItemAnswer';
import TagList from 'components/global/tag/TagList';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCaretUp, faCaretDown } from '@fortawesome/free-solid-svg-icons';
import { styled } from 'styled-components';
import QuestionEditButton from 'components/global/questionItem/QuestionEditButton';

const QuesitonButtonWrap = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 15px 0 10px;
  margin-right: 15px;
  button {
    transition: all 0.3s;
    width: 40px;
    height: 40px;
    border: 1px solid #31a8ff;
    border-radius: 100%;
    color: #31a8ff;
    font-size: 20px;
    &:hover {
      border: 1px solid #002075;
      color: #002075;
    }
  }
  p {
    margin: 7px 0;
  }
`;

const QuestionView = ({ question, likeCount, dislikeCount  }) => {
  const { id } = useParams();
  const [commentCount, setCommentCount] = useState(0);
  const [userName,setUserName] = useState();
  const { user } = useSelector((state) => state);


  const getUserData = () => {
    axios
      .get('/member/getmember', {
        headers: {
          Authorization: user.token,
        },
      }) 
      .then((response) => {
        const userData = response.data;
        setUserName(userData.data.name);
      })
      .catch((error) => {
        console.error('사용자 정보를 가져오는 중 오류 발생:', error);
      });
  };

  useEffect(() => {
    getUserData();
  }, []);

  const handleLike = async () => {
    try {
      const response = await axios.post(`/question/vote/${id}`,
        {
        voteStatus: 'LIKE',
        },
        {
          headers: {
            Authorization: user.token,
            'ngrok-skip-browser-warning': 'true',
          },
        }
      );
      console.log(response);
    } catch (error) {
      console.error(error);
    }
  };

  const handleDislike = async () => {
    try {
      await axios.post(
        `/question/vote/${id}`,
        {
          voteStatus: 'DISLIKE',
        },
        {
          headers: {
            Authorization: user.token,
            'ngrok-skip-browser-warning': 'true',
          },
        }
      );
    } catch (error) {
      console.error(error);
    }
  };
  const formatDate = (dateString) => {
    const date = new Date(dateString);
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const day = String(date.getDate()).padStart(2, '0');
    return `${year}.${month}.${day}`;
  };

  useEffect(() => {
    const fetchCommentCount = async () => {
      try {
        const response = await axios.get(`/comment/${id}`, {
          headers: {
            'ngrok-skip-browser-warning': 'true',
          },
          params: {
            page: 0,
            size: 4,
          },
        });
        const { totalElements } = response.data.pageInfo;
        setCommentCount(totalElements);
      } catch (error) {
        console.error(error);
      }
    };

    fetchCommentCount();
  }, []);

  return (
    <div>
      <h3 className="maintitle">{question.title}</h3>
      <div className="flex mt-5">
        <div className="mr-3">
          <ItemView viewCount={question.view} />
        </div>
        <div className="mx-3">
          <ItemAnswer viewAnswer={commentCount} />
        </div>
        <p className="ml-auto font-light">
          Asked: {formatDate(question.createdAt)}
        </p>
      </div>
      <div className="flex justify-between border-t-[1px] border-b-[1px] border-black/[.3] border-solid pb-2 items-center">
        <QuesitonButtonWrap>
          <button onClick={handleLike}>
            <FontAwesomeIcon icon={faCaretUp} />
          </button>
          <p>{likeCount - dislikeCount}</p>
          <button onClick={handleDislike}>
            <FontAwesomeIcon icon={faCaretDown} />
          </button>
        </QuesitonButtonWrap>
        <div className="w-full relative pt-3">
          <div className="mt-2">
          {user.token && userName === question.memberName && <QuestionEditButton id={id} className="top-[7px]" />}
          </div>
          <p
            className="text-sm font-light py-2 content"
            dangerouslySetInnerHTML={{ __html: question.content }}
          ></p>
          <TagList />
        </div>
      </div>
    </div>
  );
};

export default QuestionView;
