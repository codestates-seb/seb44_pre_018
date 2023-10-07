const LoginGoogle = () => {
  return (
    <a className="snsBtn" href="!#">
      <img
        src={require('assets/ico_google.png')}
        alt="구글 아이콘"
        className="mr-2 sm:w-[20px] sm:mr-1.5"
      />
      Sign in with Google
    </a>
  );
};

export default LoginGoogle;
