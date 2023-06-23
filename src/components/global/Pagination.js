import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faChevronLeft,
  faChevronRight,
} from '@fortawesome/free-solid-svg-icons';

const Pagination = ({ totalPosts, limit, page, setPage }) => {
  const numPages = Math.ceil(totalPosts / limit);
  return (
    <nav className="my-10 flex justify-center" aria-label="Pagination">
      <a
        href="#!"
        className="pointBu01 text-xxs px-2 py-1.5 font-extralight mx-0.5"
      >
        <span className="sr-only">Previous</span>
        <FontAwesomeIcon icon={faChevronLeft} />
      </a>
      {/* {Array(numPages).map((_, i) => {
        return (
          <a
            href="#!"
            className="pointBu01 text-xxs px-2 py-1.5 font-extralight mx-0.5"
            key={i + 1}
            onClick={() => setPage(i + 1)}
          >
            {i + 1}
          </a>
        );
      })} */}

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
