import { useState } from 'react';
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

    &:hover,
    &.active {
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
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [activeLink, setActiveLink] = useState('');

  const handleLogin = () => {
    setIsLoggedIn(true);
  };

  const handleLinkClick = (link) => {
    setActiveLink(link);
  };

  return (
    <NavbarContainer className="w-52 h-screen bg-pointCol03">
      <div className="fixed top-24 left-0 w-52 h-screen bg-pointCol03">
        <ul className="flex flex-col justify-center">
          <Link to="/" onClick={() => handleLinkClick('questions')}>
            <li className={activeLink === 'questions' ? 'active' : ''}>Questions</li>
          </Link>
          <Link to="/user" onClick={() => handleLinkClick('user')}>
            <li className={activeLink === 'user' ? 'active' : ''}>User</li>
          </Link>
          <Link to="/tag" onClick={() => handleLinkClick('tag')}>
            <li className={activeLink === 'tag' ? 'active' : ''}>Tag</li>
          </Link>
          {isLoggedIn ? (
            <Link to="/mypage" onClick={() => handleLinkClick('mypage')}>
              <li className={activeLink === 'mypage' ? 'active' : ''}>MyPage</li>
            </Link>
          ) : null}
        </ul>
      </div>
    </NavbarContainer>
  );
};

export default Navbar;