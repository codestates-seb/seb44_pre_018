import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye } from '@fortawesome/free-solid-svg-icons';

const ItemView = ({ viewCount }) => {
  return (
    <div className="mr-2">
      <span className="font-light mr-1">{viewCount}</span>
      <FontAwesomeIcon icon={faEye} />
    </div>
  );
};

export default ItemView;
