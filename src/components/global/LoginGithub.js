import ico_github from '../assets/ico_github.png';

const LoginGithub = () => {
  return (
    <a className="snsBtn" href="!#">
      <img src={ico_github} alt="깃허브 아이콘" className="mr-2" />
      Sign in with GitHub
    </a>
  );
};

export default LoginGithub;
