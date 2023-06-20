import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import axios from 'axios';
import ListItem from 'components/ListItem';
import Pagination from 'components/global/Pagination';
import SearchArea from 'components/SearchArea';

const MainList = () => {
  const [filterValue, setFilterValue] = useState('latest');
  const [queryValue, setQueryValue] = useState('');
  const [boardList, setBoardList] = useState([]);

  const getBoardList = async () => {
    try {
      const result = await axios.get('/data/data.json');
      setBoardList(result.data.questions);
    } catch (err) {
      console.log('err', err);
    }
  };

  useEffect(() => {
    getBoardList();
  }, [filterValue, queryValue]);

  return (
    <div className="inner">
      <div className="flex items-center justify-between">
        <h3 className="maintitle">All Questins</h3>
        <Link className="pointBu01" to="/noticewrite">
          Ask Qustion
        </Link>
      </div>

      <SearchArea
        filterValue={filterValue}
        setFilterValue={setFilterValue}
        queryValue={queryValue}
        setQueryValue={setQueryValue}
      />

      <ul className="border-t-[1px] border-black/[.3] border-solid">
        {boardList.map((item, idx) => {
          return <ListItem key={item.questionId} value={item} />;
        })}
      </ul>
      <Pagination />
    </div>
  );
};

export default MainList;
