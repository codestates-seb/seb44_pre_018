import { useEffect, useState } from 'react';
import { BrowserRouter, Routes, Route, useLocation } from 'react-router-dom';
import Header from 'components/global/Header';
import Navbar from 'components/global/Navbar';
import Footer from 'components/global/Footer';
import MainList from 'pages/MainList';
import Login from 'pages/Login';
import Register from 'pages/Register';
import DetailPage from 'pages/DetailPage';
import NoticeWrite from 'pages/QuestionWrite';
import MyPage from 'pages/MyPage';
import Tag from 'pages/Tag';
import User from 'pages/User';

const Router = () => {
  const hideNavbar = ['/login', '/Login', '/register', '/Register'];
  const [isNav, setIsNav] = useState(
    hideNavbar.includes(window.location.pathname)
  );
  return (
    <>
      <BrowserRouter>
        <Header />
        <LocationCheck setIsNav={setIsNav} hideNavbar={hideNavbar} />
        {isNav ? (
          <div
            className="LoginBox"
            style={{
              backgroundImage: `url(${require('assets/login_bg.png')})`,
            }}
          >
            <Routes>
              <Route path="/login" element={<Login />} />
              <Route path="/register" element={<Register />} />
            </Routes>
          </div>
        ) : (
          <section className="flex">
            <Navbar />
            <div className="mainWrap">
              <Routes>
                <Route path="/" element={<MainList />} />
                <Route path="/write" element={<QuestionWrite />} />
                <Route path="/detail/:id" element={<DetailPage />} />
                <Route path="/noticewrite" element={<NoticeWrite />} />
                <Route path="/mypage" element={<MyPage />} />
                <Route path="/tag" element={<Tag />} />
                <Route path="/user" element={<User />} />
              </Routes>
            </div>
          </section>
        )}

        <Footer />
      </BrowserRouter>
    </>
  );
};
export const LocationCheck = ({ setIsNav, hideNavbar }) => {
  const location = useLocation();
  useEffect(() => {
    setIsNav(hideNavbar.includes(window.location.pathname));
  }, [location]);
  return null;
};

export default Router;
