import { useEffect, useState } from 'react';
import axios from 'axios';
import SearchBar from 'components/SearchBar';
import UserItem from 'components/UserWrap';
import Pagination from 'components/global/Pagination';

const User = () => {
  const [users, setUsers] = useState([]);

  const getUserData = async () => {
    try {
      const result = await axios.get('/member/1', {
        headers: {
          'ngrok-skip-browser-warning': 'true',
        },
      });
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
