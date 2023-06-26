import { Link, useNavigate } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { useState } from 'react';
import { loginUser } from 'store';
import axios from 'axios';
import LoginGoogle from 'components/global/login/LoginGoogle';
import LoginGithub from 'components/global/login/LoginGithub';
import { getCookie, setCookie, removeCookie } from './cookie';

const Login = () => {
  const dispatch = useDispatch();
  const navigate = useNavigate();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [checkEmail, setCheckEmail] = useState(false);
  const [checkPassword, setCheckPassword] = useState(false);

  const emailChange = (e) => {
    setCheckEmail(false);
    setEmail(e.currentTarget.value);
  };
  const passwordChange = (e) => {
    setCheckPassword(false);
    setPassword(e.currentTarget.value);
  };

  const onSubmitHandler = async (e) => {
    e.preventDefault();
    setCheckEmail(email === '');
    setCheckPassword(password === '');
    try {
      const result = await axios.post('/auth/login', {
        username: email,
        password: password,
      });
      const accessToken = result.headers.authorization;
      setCookie('Authorization', `${accessToken}`);
      console.log(result);
      dispatch(
        loginUser({
          name: 'test',
          email: email,
          isLoading: false,
          isLogin: true,
          token: accessToken,
        })
      );
      navigate('/');
    } catch (err) {
      console.log(err);
    }

    // const result = await axios.post('/auth/login', {
    //   username: email,
    //   password: password,
    // });
    // const member_no = result.data.member_id;
    // console.log(member_no);
    // const result2 = await axios.get(`/member/${member_no}`);
    // dispatch(loginUser({ ...result2.data, isLogin: true }));

    // dispatch(
    //   loginUser({
    //     name: 'test',
    //     email: email,
    //     isLoading: false,
    //     isLogin: true,
    //   })
    // );
    //navigate('/');
  };

  return (
    <div className="flex flex-col items-center w-[26rem] pt-10">
      <LoginGoogle />
      <LoginGithub />
      <form className="LoginForm mt-4" onSubmit={onSubmitHandler}>
        <label className="labelText pt-0" htmlFor="email">
          Email
        </label>
        <input
          type="text"
          id="email"
          value={email}
          onChange={emailChange}
          className={`inputBox ${checkEmail && 'disabledInput'}`}
        />
        {checkEmail && <p className="notice">이메일은 필수 입력해야 합니다.</p>}

        <label className="labelText" htmlFor="password">
          Password
        </label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={passwordChange}
          className={`inputBox ${checkPassword && 'disabledInput'}`}
        />
        {checkPassword && (
          <p className="notice">비밀번호는 필수 입력해야 합니다.</p>
        )}

        <button type="submit" className="pointBu02 w-full mt-10">
          Log in
        </button>
      </form>
      <p className="mb-7 sm:text-sm">
        <span>Don’t have an account?</span>
        <Link
          className="transition ml-2 text-pointCol05 border-b-[1px] border-pointCol05 hover:text-pointCol02 hover:border-pointCol02"
          to="/Register"
        >
          Sign up
        </Link>
      </p>
    </div>
  );
};

export default Login;
