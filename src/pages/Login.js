import { Link } from 'react-router-dom';
import LoginGoogle from 'components/global/login/LoginGoogle';
import LoginGithub from 'components/global/login/LoginGithub';

const Login = () => {
  return (
    <div className="flex flex-col items-center w-[26rem] pt-10">
      <LoginGoogle />
      <LoginGithub />
      <form className="LoginForm mt-4">
        <label className="labelText pt-0" htmlFor="email">
          Email
        </label>
        <input type="text" id="email" className="inputBox" />
        <label className="labelText" htmlFor="password">
          Password
        </label>
        <input type="password" id="password" className="inputBox" />
        <button type="submit" className="pointBu02 w-full mt-10">
          Log in
        </button>
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
