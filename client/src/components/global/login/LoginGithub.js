const LoginGithub = () => {
  return (
    <a className="snsBtn" href="!#">
      <img
        src={require('assets/ico_github.png')}
        alt="깃허브 아이콘"
        className="mr-2 sm:w-[20px] sm:mr-1.5"
      />
      Sign in with GitHub
    </a>
  );
};

export default LoginGithub;
