import { useState } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const ButtonContainer = styled.div`
  display: flex;
  justify-content: center;
  gap: 1rem;
`;

const Button = () => {
  // 로그인 상태를 저장하는 상태 변수
  const [isLoggedIn, setIsLoggedIn] = useState(false);

  // 로그아웃 시 상태 변경
  const handleLogout = () => {
    setIsLoggedIn(false);
  };

  return (
    <ButtonContainer>
      {isLoggedIn ? (
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
