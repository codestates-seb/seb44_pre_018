import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faEye } from '@fortawesome/free-solid-svg-icons';

const ItemView = () => {
  return (
    <div className="mr-2">
      <span className="font-light mr-1">1</span>
      <FontAwesomeIcon icon={faEye} />
    </div>
  );
};

export default ItemView;
