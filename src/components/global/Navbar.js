import { Link } from 'react-router-dom';
import styled from 'styled-components';

const NavbarContainer = styled.div`
  @media (max-width: 981px) {
    display: none;
  }
  li {
    padding-top: 20px;
    padding-bottom: 20px;
    padding-left: 10px;
    &:hover {
      background: linear-gradient(to right, #b5c4ea 95%, #002075 5%);
    }
  }
  a {
    color: #000;
    &:hover {
      color: #fff;
    }
  }
`;

const Navbar = () => {
  return (
    <NavbarContainer className="w-52 h-screen bg-pointCol03">
      <div className="fixed top-24 left-0 w-52 h-screen bg-pointCol03">
        <ul className="flex flex-col justify-center">
          <li>
            <Link to="/questions">Questions</Link>
          </li>
          <li>
            <Link to="/user">User</Link>
          </li>
          <li>
            <Link to="/tag">Tag</Link>
          </li>
          <li>
            <Link to="/mypage">MyPage</Link>
          </li>
        </ul>
      </div>
    </NavbarContainer>
  );
};

export default Navbar;
