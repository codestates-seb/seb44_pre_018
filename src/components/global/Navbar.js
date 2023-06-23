import { useState } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const NavbarContainer = styled.div`
  position: relative;
  z-index: 1;
  li {
    padding-top: 20px;
    padding-bottom: 20px;
    padding-left: 10px;

    &.active {
      background: linear-gradient(to right, #b5c4ea 95%, #002075 5%);
    }

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

  @media (max-width: 981px) {
    position: absolute;
    transition: all 0.2s;
    left: -100%;
    .headerLeftMenu {
      left: -100%;
    }
    &.on {
      left: 0;
      &::after {
        content: '';
        display: block;
        position: fixed;
        left: 0;
        top: 0;
        width: 100%;
        height: 100%;
        background-color: rgba(0, 0, 0, 0.5);
        z-index: -1;
      }
      .headerLeftMenu {
        left: 0;
      }
    }
  }
`;

const Navbar = ({ setNavbarOpen, isNavbarOpen }) => {
  const [isLoggedIn, setIsLoggedIn] = useState(false);
  const [activeLink, setActiveLink] = useState('');
  const handleNavbarToggle = () => {
    setNavbarOpen(false);
  };
  const handleLogin = () => {
    setIsLoggedIn(true);
  };

  const handleLinkClick = (link) => {
    setActiveLink(link);
  };

  return (
    <NavbarContainer
      onClick={handleNavbarToggle}
      className={`w-52 pt-24  h-screen bg-pointCol03 ${
        isNavbarOpen ? 'on' : null
      }`}
    >
      <div className="headerLeftMenu left-0 w-52 h-full bg-pointCol03 fixed">
        <ul className="flex flex-col justify-center">
          <li key="questions" className={activeLink === '/' ? 'active' : ''}>
            <Link to="/" onClick={() => handleLinkClick('/')}>
              Questions
            </Link>
          </li>
          <li key="user" className={activeLink === 'user' ? 'active' : ''}>
            <Link to="/user" onClick={() => handleLinkClick('user')}>
              User
            </Link>
          </li>
          <li key="tag" className={activeLink === 'tag' ? 'active' : ''}>
            <Link to="/tag" onClick={() => handleLinkClick('tag')}>
              Tag
            </Link>
          </li>
          {isLoggedIn && (
            <li
              key="mypage"
              className={activeLink === 'mypage' ? 'active' : ''}
            >
              <Link to="/mypage" onClick={() => handleLinkClick('mypage')}>
                MyPage
              </Link>
            </li>
          )}
        </ul>
      </div>
    </NavbarContainer>
  );
};

export default Navbar;
