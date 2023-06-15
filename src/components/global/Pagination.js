import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faChevronLeft,
  faChevronRight,
} from '@fortawesome/free-solid-svg-icons';

const Pagination = () => {
  return (
    <nav className="my-10 flex justify-center" aria-label="Pagination">
      <a
        href="#!"
        className="pointBu01 text-xxs px-2 py-1.5 font-extralight mx-0.5"
      >
        <span className="sr-only">Previous</span>
        <FontAwesomeIcon icon={faChevronLeft} />
      </a>
      <a
        href="#!"
        className="pointBu01 text-xxs px-2 py-1.5 font-extralight mx-0.5"
      >
        1
      </a>
      <a
        href="#!"
        className="pointBu01 text-xxs px-2 py-1.5 font-extralight mx-0.5"
      >
        2
      </a>
      <a
        href="#!"
        className="pointBu01 text-xxs px-2 py-1.5 font-extralight mx-0.5"
      >
        3
      </a>
      <a
        href="#!"
        className="pointBu01 text-xxs px-2 py-1.5 font-extralight mx-0.5"
      >
        4
      </a>
      <a
        href="#!"
        className="pointBu01 text-xxs px-2 py-1.5 font-extralight mx-0.5"
      >
        5
      </a>
      <a
        href="#!"
        className="pointBu01 text-xxs px-2 py-1.5 font-extralight mx-0.5"
      >
        <span className="sr-only">Previous</span>
        <FontAwesomeIcon icon={faChevronRight} />
      </a>
    </nav>
  );
};

export default Pagination;
