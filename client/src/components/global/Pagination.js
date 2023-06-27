import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import {
  faChevronLeft,
  faChevronRight,
} from '@fortawesome/free-solid-svg-icons';
import { useEffect, useState } from 'react';
import { PAGES_PER_ARRAY, POST_SIZE } from 'pages/MainList';
import { useLocation, useNavigate } from 'react-router';
import { getSearch } from 'assets/js/common';
import { styled } from 'styled-components';
const Pagination = ({ page, totalPages }) => {
  const [numPages, setNumPages] = useState([]);
  const navigate = useNavigate();
  const location = useLocation();
  const handlePage = (e) => {
    const {
      currentTarget: { value },
    } = e;
    const searchObj = getSearch();
    const newObj = { ...searchObj, page: value };
    const searchParams = new URLSearchParams(newObj).toString();
    navigate({
      pathname: location.pathname,
      search: searchParams,
    });
  };
  const generateNumPages = () => {
    let arr = [];
    const startPage = parseInt(page / PAGES_PER_ARRAY) * PAGES_PER_ARRAY + 1;
    const endPage =
      startPage + PAGES_PER_ARRAY > totalPages
        ? totalPages
        : startPage + PAGES_PER_ARRAY;
    for (let i = startPage; i <= endPage; i++) {
      arr.push(i);
    }
    setNumPages(arr);
    /*
    5단위로 한다면, 
    5로 나누고, 그 몫으로 시작점과, 끝점을 구한다.
    단, 끝점 > totalPages라면, 끝점은 totalPages다.
     */
  };
  useEffect(() => {
    generateNumPages();
  }, [totalPages, page]);
  return (
    <>
      {numPages.length > 0 && (
        <nav className="my-10 flex justify-center" aria-label="Pagination">
          <button
            onClick={handlePage}
            value={numPages[0] - 1}
            disabled={numPages[0] - 1 === 0}
            className="pointBu01 text-xxs px-2 py-1.5 font-extralight mx-0.5"
          >
            <span className="sr-only">Previous</span>
            <FontAwesomeIcon icon={faChevronLeft} />
          </button>
          {numPages.map((thisPage) => {
            const currentPage = parseInt(thisPage) === parseInt(page);
            return (
              <button
                className={`text-xxs px-2 py-1.5 font-extralight mx-0.5 ${
                  currentPage ? 'pointBu02' : 'pointBu01'
                }`}
                value={thisPage}
                key={thisPage}
                onClick={handlePage}
              >
                {thisPage}
              </button>
            );
          })}
          <button
            onClick={handlePage}
            value={numPages[numPages.length - 1] + 1}
            disabled={numPages[numPages.length - 1] + 1 >= totalPages}
            className="pointBu01 text-xxs px-2 py-1.5 font-extralight mx-0.5"
          >
            <span className="sr-only">next</span>
            <FontAwesomeIcon icon={faChevronRight} />
          </button>
        </nav>
      )}
    </>
  );
};

export default Pagination;
