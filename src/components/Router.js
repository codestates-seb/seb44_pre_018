import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import MainList from '../pages/MainList';
import Login from '../pages/Login';
import Register from '../pages/Register';
import DetailPage from '../pages/DetailPage';
import NoticeWrite from '../pages/NoticeWrite';

const Router = () => {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<MainList />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/write" element={<NoticeWrite />} />
          <Route path="/detail/:id" element={<DetailPage />} />
          <Route path="/noticewrite" element={<NoticeWrite />} />
          <Route path="*" element={<Navigate replace to="/" />} />
        </Routes>
      </BrowserRouter>
    </>
  );
};

export default Router;
