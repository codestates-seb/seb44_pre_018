import { BrowserRouter, Link } from 'react-router-dom';

const Navbar = () => {
  return (
    <BrowserRouter>
      <div className="w-52 h-screen bg-pointCol03">
        <div className="fixed top-24 left-0 w-52 h-screen bg-pointCol03">
          <div className="flex flex-col justify-center items-center">
            <Link to="/questions" className="navButton mt-5 mb-5">
              Questions
            </Link>
            <Link to="/user" className="navButton mt-5 mb-5">
              User
            </Link>
            <Link to="/tag" className="navButton mt-5 mb-5">
              Tag
            </Link>
            <Link to="/mypage" className="navButton mt-5 mb-5">
              MyPage
            </Link>
          </div>
        </div>
      </div>
    </BrowserRouter>
  );
};

export default Navbar;
