import { Link } from 'react-router-dom';
import Filter from '../components/Filter';
import SearchBar from '../components/SearchBar';
import ListItem from '../components/ListItem';
import Pagination from '../components/global/Pagination';

const MainList = () => {
  return (
    <div className="inner">
      <div className="flex items-center justify-between">
        <h3 className="maintitle">All Questins</h3>
        <Link to="/noticewrite">Ask Qustion</Link>
      </div>
      <div className="flex items-center justify-between">
        <Filter />
        <SearchBar />
      </div>
      <ul>
        <ListItem />
      </ul>
      <Pagination />
    </div>
  );
};

export default MainList;
