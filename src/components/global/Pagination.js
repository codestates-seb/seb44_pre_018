import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faChevronLeft,
  faChevronRight,
} from '@fortawesome/free-solid-svg-icons';

const Pagination = () => {
  return (
    <nav
      className="isolate inline-flex -space-x-px rounded-md shadow-sm"
      aria-label="Pagination"
    >
      <a href="#!" className="">
        <span className="sr-only">Previous</span>
        <FontAwesomeIcon icon={faChevronLeft} />
      </a>
      <a href="#!" className="">
        1
      </a>
      <a href="#!" className="">
        2
      </a>
      <a href="#!" className="">
        3
      </a>
      <a href="#!" className="">
        4
      </a>
      <a href="#!" className="">
        5
      </a>
      <a href="#!" className="">
        <span className="sr-only">Previous</span>
        <FontAwesomeIcon icon={faChevronRight} />
      </a>
    </nav>
  );
};

export default Pagination;
