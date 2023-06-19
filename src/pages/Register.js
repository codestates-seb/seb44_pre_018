import { Link } from 'react-router-dom';
import LoginGoogle from 'components/global/login/LoginGoogle';
import LoginGithub from 'components/global/login/LoginGithub';

const Register = () => {
  return (
    <div className="flex flex-col items-center w-[26rem]">
      <form className="LoginForm">
        <label className="labelText pt-0" htmlFor="name">
          Display name
        </label>
        <input type="text" id="name" className="inputBox" />
        <label className="labelText" htmlFor="phone">
          Phone
        </label>
        <input type="text" id="phone" className="inputBox" />
        <label className="labelText" htmlFor="email">
          Email
        </label>
        <input type="text" id="email" className="inputBox" />
        <label className="labelText" htmlFor="password">
          Password
        </label>
        <input type="password" id="password" className="inputBox" />
        <button type="submit" className="pointBu02 w-full mt-10">
          Sign up
        </button>
      </form>
      <p className="mb-7">
        <span>Already have an account?</span>
        <Link
          className="transition ml-2 text-pointCol05 border-b-[1px] border-pointCol05 hover:text-pointCol02 hover:border-pointCol02"
          to="/Login"
        >
          Log in
        </Link>
      </p>
      <LoginGoogle />
      <LoginGithub />
    </div>
  );
};

export default Register;
