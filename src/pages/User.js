import { useEffect, useState } from 'react';
import SearchBar from '../components/SearchBar';
import UserItem from '../components/UserItem';
import Pagination from '../components/global/Pagination';
import axios from 'axios';

const User = () => {
  const [users, setUsers] = useState([]);
  const getUserData = async () => {
    try {
      const result = await axios.get('/data/member.json');
      console.log(result.data);
      setUsers(result.data.member);
    } catch (err) {
      console.log('err', err);
    }
  };
  useEffect(() => {
    getUserData();
  }, []);
  return (
    <div className="inner">
      <h3 className="maintitle mb-4">User</h3>
      <SearchBar />
      <UserItem users={users} />
      <Pagination />
    </div>
  );
};

export default User;
