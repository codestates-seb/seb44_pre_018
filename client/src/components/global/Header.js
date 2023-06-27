// 1. 비회원일 때, login signup 버튼 출력
// 2. 회원일 경우, mypage logout 버튼 출력
// 3. 태블릿 사이즈로 줄일 경우, navbar사라지고 햄버거 버튼 출력
import { Link, useLocation } from 'react-router-dom';
import { useState, useEffect } from 'react';
import HeaderButton from 'components/global/login/HeaderButton';
import Navbar from 'components/global/Navbar';
// import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
// import { faBars, faSortDown } from '@fortawesome/free-solid-svg-icons';

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
    align-items: center;
    width: 100vw;
  }

  .logo-img {
    display: block;
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
    width: 43px;
    height: 25px;
    position: relative;
    cursor: pointer;
    span {
      position: absolute;
      width: 100%;
      height: 4px;
      border-radius: 3px;
      transition: all 0.3s;
      background-color: #002075;
      &:nth-of-type(1) {
        top: 0;
      }
      &:nth-of-type(2) {
        top: 50%;
        transform: translateY(-50%);
      }
      &:nth-of-type(3) {
        bottom: 0;
      }
    }
    @media (max-width: 981px) {
      display: block;
      /* padding-right: 20rem; */
      &.on {
        span {
          &:nth-of-type(1) {
            top: 50%;
            transform: translateY(-50%) rotate(45deg);
          }
          &:nth-of-type(2) {
            opacity: 0;
          }
          &:nth-of-type(3) {
            bottom: unset;
            top: 50%;
            transform: translateY(-50%) rotate(-45deg);
          }
        }
      }
    }
  }
`;

const Header = ({ isNavbarOpen, setNavbarOpen }) => {
  const location = useLocation();
  const hideNavbar = ['/login', '/Login', '/register', '/Register'];
  const [isNav, setIsNav] = useState(
    hideNavbar.includes(window.location.pathname)
  );

  const handleNavbarToggle = () => {
    setNavbarOpen(!isNavbarOpen);
  };
  useEffect(() => {
    setIsNav(hideNavbar.includes(window.location.pathname));
  }, [location]);

  return (
    <>
      <HeaderContainer>
        {!isNav ? (
          <div
            className={`menu-icon ${isNavbarOpen ? 'on' : null}`}
            onClick={handleNavbarToggle}
          >
            <span></span>
            <span></span>
            <span></span>
          </div>
        ) : null}

        <div
          className={`header-wrapper justify-between sm:justify-end ${
            isNav ? ' sm:justify-between' : null
          }`}
        >
          {isNav ? (
            <Link to="/" className="headerLogo block">
              <img
                src={require('assets/logo-w.png')}
                alt="logo"
                className="logo-img sm:w-[7.5rem] sm:h-auto"
              />
            </Link>
          ) : (
            <Link to="/" className="headerLogo sm:hidden">
              <img
                src={require('assets/logo-w.png')}
                alt="logo"
                className="logo-img w-[9rem]"
              />
            </Link>
          )}
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
    </>
  );
};

export default Header;
