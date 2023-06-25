import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMessage } from '@fortawesome/free-solid-svg-icons';

const ItemAnswer = ({ commentCount }) => {
  return (
    <div>
      <span className="font-light mr-1"> {commentCount} </span>
      <FontAwesomeIcon icon={faMessage} />
    </div>
  );
};

export default ItemAnswer;
