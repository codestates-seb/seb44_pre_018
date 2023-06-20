import { Link } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { useEffect, useState } from 'react';
import { loginUser } from 'store';
import axios from 'axios';
import LoginGoogle from 'components/global/login/LoginGoogle';
import LoginGithub from 'components/global/login/LoginGithub';

const Login = () => {
  const dispatch = useDispatch();

  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  const [msg, setMsg] = useState('');

  const onEmailHandler = (event) => {
    setEmail(event.currentTarget.value);
  };
  const onPasswordHandler = (event) => {
    setPassword(event.currentTarget.value);
  };
  const onSubmitHandler = (event) => {
    event.preventDefault();

    let body = {
      email: email,
      password: password,
    };
    // const result = await axios.post('/api/login', body);
    // const member_no = result.data.member_no;
    // const result2 = await axios.get(`/api/member/${member_no}`);
    // dispatch(loginUser({...result2.data, isLogin : true}))

    dispatch(loginUser(body));
  };
  useEffect(() => {
    if (msg) {
      setTimeout(() => {
        setMsg('');
      }, 1500);
    }
  }, [msg]);

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
          className="inputBox"
          onChange={onEmailHandler}
        />
        <label className="labelText" htmlFor="password">
          Password
        </label>
        <input
          type="password"
          id="password"
          className="inputBox"
          onChange={onPasswordHandler}
        />
        <button type="submit" className="pointBu02 w-full mt-10">
          Log in
        </button>
        {msg}
      </form>
      <p className="mb-7">
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
