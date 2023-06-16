import { BrowserRouter, Routes, Route } from 'react-router-dom';
import MainList from '../pages/MainList';
import Login from '../pages/Login';
import Register from '../pages/Register';
import DetailPage from '../pages/DetailPage';
import NoticeWrite from '../pages/NoticeWrite';
import Header from '../components/global/Header';
import Navbar from '../components/global/Navbar';
import Footer from '../components/global/Footer';
import login_bg from './assets/login_bg.png';
import Tag from '../pages/Tag';
import User from '../pages/User';

const Router = () => {
  const hideNavbar = ['/login', '/Login', '/register', '/Register'];
  return (
    <>
      <BrowserRouter>
        <Header />
        {hideNavbar.includes(window.location.pathname) ? (
          <div
            className="LoginBox"
            style={{ backgroundImage: `url(${login_bg})` }}
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
                <Route path="/write" element={<NoticeWrite />} />
                <Route path="/detail/:id" element={<DetailPage />} />
                <Route path="/noticewrite" element={<NoticeWrite />} />
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

export default Router;
