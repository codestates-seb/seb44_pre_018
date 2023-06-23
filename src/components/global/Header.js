// 1. 비회원일 때, login signup 버튼 출력
// 2. 회원일 경우, mypage logout 버튼 출력
// 3. 태블릿 사이즈로 줄일 경우, navbar사라지고 햄버거 버튼 출력
import { Link } from 'react-router-dom';
import { useState, useEffect } from 'react';
import HeaderButton from 'components/global/login/HeaderButton';
import Navbar from 'components/global/Navbar';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faBars, faSortDown } from '@fortawesome/free-solid-svg-icons';

import styled from 'styled-components';

const HeaderContainer = styled.header`
  position: fixed;
  top: 0;
  left: 0;
  width: 100vw;
  height: 6rem;
  max-width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0 2rem;
  background-color: #ffffff;
  box-shadow: 0px 8px 8px rgba(0, 0, 0, 0.1);
  -webkit-box-shadow: 0px 8px 8px rgba(0, 0, 0, 0.1);
  -moz-box-shadow: 0px 8px 8px rgba(0, 0, 0, 0.1);
  z-index: 2;

  .header-wrapper {
    display: flex;
    justify-content: space-between;
    align-items: center;
    width: 100vw;
  }

  .logo-img {
    display: block;
    width: 10rem;
    height: 3.5rem;
  }

  .profile-img {
    display: block;
    width: 4rem;
    height: 3.5rem;
    border-radius: 50%;
    box-shadow: 0px 2px 4px rgba(0, 0, 0, 0.2);
  }

  .right-section {
    display: flex;
    justify-content: space-between;
    align-items: center;
    gap: 1rem;
  }
  .menu-icon {
    display: none;
    @media (max-width: 981px) {
      display: block;
      /* padding-right: 20rem; */
      width: 5rem;
      height: 2rem;
      cursor: pointer;
    }
  }
  @media (max-width: 981px) {
    .headerLogo {
      display: none;
    }
    .header-wrapper {
      justify-content: flex-end;
    }
  }
`;

const Header = () => {
  const [isNavbarOpen, setNavbarOpen] = useState(false);

  const handleNavbarToggle = () => {
    setNavbarOpen(!isNavbarOpen);
  };

  return (
    <>
      <HeaderContainer>
        <FontAwesomeIcon 
        icon={faBars} 
        className="menu-icon"
        onClick={handleNavbarToggle}
         />
        <div className="header-wrapper">
          <Link to="/" className="headerLogo">
            <img
              src={require('assets/logo-w.png')}
              alt="logo"
              className="logo-img"
            />
          </Link>
          <div className="right-section">
            <HeaderButton />
            <img
              src={require('assets/profile_image1.jpeg')}
              alt="profile"
              className="profile-img"
            />
            {/* <FontAwesomeIcon icon={faSortDown} className="text-2xl" /> */}
          </div>
        </div>
      </HeaderContainer>
      {isNavbarOpen && (<Navbar />)}
    </>
  );
};

export default Header;
