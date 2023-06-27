import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faMessage } from '@fortawesome/free-solid-svg-icons';

const ItemAnswer = ({ viewAnswer }) => {
  return (
    <div>
      <span className="font-light mr-1"> {viewAnswer} </span>
      <FontAwesomeIcon icon={faMessage} />
    </div>
  );
};

export default ItemAnswer;
