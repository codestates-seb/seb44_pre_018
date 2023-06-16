import ico_google from '../assets/ico_google.png';

const LoginGoogle = () => {
  return (
    <a className="snsBtn" href="!#">
      <img src={ico_google} alt="구글 아이콘" className="mr-2" />
      Sign in with Google
    </a>
  );
};

export default LoginGoogle;
