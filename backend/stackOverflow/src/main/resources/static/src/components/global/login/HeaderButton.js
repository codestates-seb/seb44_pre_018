import { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { getCookie } from 'pages/cookie';
import { useDispatch, useSelector } from 'react-redux';
import { clearUser } from 'store';

const ButtonContainer = styled.div`
  display: flex;
  justify-content: center;
  gap: 1rem;
`;

const Button = () => {
  const dispatch = useDispatch();

  const { user } = useSelector((state) => state);
  // 로그아웃 시 상태 변경
  const handleLogout = () => {
    dispatch(clearUser());
  };

  return (
    <ButtonContainer>
      {user.isLogin ? (
        <>
          <Link className="pointBu01" to="/mypage">
            MyPage
          </Link>
          <button className="pointBu02" onClick={handleLogout}>
            Logout
          </button>
        </>
      ) : (
        <>
          <Link className="pointBu01" to="/login">
            Login
          </Link>
          <Link className="pointBu02" to="/register">
            Signup
          </Link>
        </>
      )}
    </ButtonContainer>
  );
};

export default Button;
