import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom';
import MainList from '../pages/MainList';
import Login from '../pages/Login';
import Register from '../pages/Register';
import DetailPage from '../pages/DetailPage';

const Router = () => {
  return (
    <>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<MainList />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Register />} />
          <Route path="/detail/:id" element={<DetailPage />} />
          <Route path="*" element={<Navigate replace to="/" />} />
        </Routes>
      </BrowserRouter>
    </>
  );
};

export default Router;
