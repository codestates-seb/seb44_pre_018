import SearchBar from '../components/SearchBar';
import UserItem from '../components/UserItem';
import Pagination from '../components/global/Pagination';

const User = () => {
  return (
    <div className="inner">
      <h3 className="maintitle mb-4">User</h3>
      <SearchBar />
      <UserItem />
      <Pagination />
    </div>
  );
};

export default User;
