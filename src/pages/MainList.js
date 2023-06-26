import { useEffect, useState } from 'react';
import { Link, useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import ListItem from 'components/ListItem';
import Pagination from 'components/global/Pagination';
import SearchArea from 'components/SearchArea';
import { getSearch } from 'assets/js/common';
import { styled } from 'styled-components';
import { getCookie } from './cookie';
export const POST_SIZE = 10;
export const PAGES_PER_ARRAY = 10;
const MainList = () => {
  const searchData = getSearch();
  const location = useLocation();
  const navigate = useNavigate();
  const token = getCookie('Authorization');

  const [queryStrObj, setQueryStrObj] = useState({
    size: POST_SIZE,
    keyword: searchData.keyword ? searchData.keyword : '',
    sortBy: searchData.sortBy ? searchData.sortBy : '',
    page: searchData.page ? searchData.page : 1,
  });
  const [boardList, setBoardList] = useState([]);
  const [totalPages, setTotalPages] = useState(0);
  const [totalEl, setTotalEl] = useState(0);
  const generateQueryStr = (obj) => {
    let str = '';
    for (let i = 0; i < Object.keys(obj).length; i++) {
      const key = Object.keys(obj)[i];
      if (obj[key] === '') continue;
      if (i !== 0) str += '&';
      str += `${key}=${obj[key]}`;
    }
    return str;
  };
  const getBoardList = async () => {
    const qs = generateQueryStr(queryStrObj);
    console.log(qs);
    try {
      const {
        data: { data: boardList, pageInfo },
      } = await axios.get(`/question/search?${qs}`, {
        headers: {
          'ngrok-skip-browser-warning': 'true',
          Authorization: token,
        },
      });
      setBoardList(boardList);
      setTotalPages(pageInfo.totalPages);
      setTotalEl(pageInfo.totalElements);
    } catch (err) {
      console.log('err', err);
    }
  };

  useEffect(() => {
    getBoardList();
  }, [queryStrObj]);
  useEffect(() => {
    const searchData = getSearch();
    const obj = {
      size: POST_SIZE,
      keyword: searchData.keyword ? searchData.keyword : '',
      sortBy: searchData.sortBy ? searchData.sortBy : '',
      page: searchData.page ? searchData.page : 1,
    };
    setQueryStrObj(obj);
  }, [location]);
  // const postsData = (posts) => {
  //   if (posts) {
  //     let result = posts.slice(offset, offset + ;o,o);
  //     return result;
  //   }
  // };

  return (
    <div className="inner">
      <div className="flex items-center justify-between">
        <h3 className="maintitle">All Questins</h3>
        <Link className="pointBu01" to="/questionwrite">
          Ask Qustion
        </Link>
      </div>

      <SearchArea />

      {boardList.length > 0 ? (
        <>
          <ul className="border-t-[1px] border-black/[.3] border-solid">
            {boardList.map((item, idx) => {
              return <ListItem key={item.questionId} value={item} />;
            })}
          </ul>
          <Pagination page={queryStrObj.page} totalPages={totalPages} />
        </>
      ) : (
        <NoData text="게시글이" />
      )}
    </div>
  );
};

export default MainList;
const EmptyArea = styled.div`
  padding: 80px 0;
  font-size: 20px;
  text-align: center;
`;
const NoData = ({ text = '데이터가' }) => {
  return <EmptyArea>표시할 {text} 없습니다.</EmptyArea>;
};
