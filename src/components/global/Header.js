// 1. 비회원일 때, login signup 버튼 출력
// 2. 회원일 경우, mypage logout 버튼 출력
// 3. 태블릿 사이즈로 줄일 경우, navbar사라지고 햄버거 버튼 출력
import { useState } from 'react';
import { BrowserRouter, Link } from 'react-router-dom';

const Header = () => {
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  const toggleMenu = () => {
    setIsMenuOpen(!isMenuOpen);
  };

  return (
    <BrowserRouter>
      <header className="flex justify-between items-center h-24 w-screen bg-white shadow-md fixed top-0 left-0">
        <div className="flex items-center w-screen">
          <img
            src={process.env.PUBLIC_URL + '/logo-w.png'}
            alt="logo"
            className="h-15 mr-4"
          />
        </div>
        <div className="flex items-center">
          <Link className="pointBu01 mr-4" to="/login">
            Login
          </Link>
          <Link className="pointBu02 mr-4" to="/">
            Signup
          </Link>
          <img
            src={process.env.PUBLIC_URL + '/profile1.jpeg'}
            alt="profile"
            className="h-12 w-12 rounded-full object-cover"
          />
        </div>

        <div className="text-lg">
          {/* 테블릿 사이즈 이상일 때는 햄버거 버튼 출력 */}
          <button className="block lg:hidden" onClick={toggleMenu}>
            <i className="fas fa-bars"></i> {/* 햄버거 버튼 아이콘 */}
          </button>
        </div>
        {/* 테블릿 사이즈 이하일 때는 모바일 메뉴 출력 */}
        <nav
          className={`lg:hidden ${
            isMenuOpen ? 'translate-x-0' : 'translate-x-full'
          } transition-transform duration-300 ease-in-out`}
        >
          {/* 네비게이션 바 내용 */}
        </nav>
      </header>
    </BrowserRouter>
  );
};

export default Header;
