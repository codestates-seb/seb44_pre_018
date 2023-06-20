import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowUpFromBracket } from '@fortawesome/free-solid-svg-icons';
import styled from 'styled-components';

const Container = styled.div`
  display: flex;
  margin-top: 2rem;

  .left-section {
    display: flex;
    flex-direction: column;
    align-items: center;
    margin-right: 2rem;
  }

  .light-section {
    display: flex;
    flex-direction: column;
    margin-left: 2rem;
  }

  img {
    width: 10rem;
    height: 10rem;
    border-radius: 50%;
  }

  span {
    border-bottom: 2px solid #ccc;
    margin: 2rem 2rem 0 0;
  }
`;

const Button = styled.button`
  width: 10rem;
  margin-top: 1rem;
`;

const ProfileDetails = styled.div`
  display: flex;
  flex-direction: column;
  margin-bottom: 1rem;
`;

const MyPage = () => {
  return (
    <div className="inner">
      <h3 className="maintitle">MyPage</h3>
      <h1 className="mt-3">Hello,Nickname!</h1>
      <Container>
        <div className="left-section">
          <img src={require('assets/profile_image1.jpeg')} alt="Profile" />
          <Button className="pointBu01 w-50 text-sm">
            <FontAwesomeIcon icon={faArrowUpFromBracket} className="mr-1"/>
              change picture
            </Button>
          <Button className="pointBu03">Edit Profile</Button>
        </div>
        <div className="light-section">
          <ProfileDetails>
            <span>Name: 냥냥씨</span>
            <span>Email: nyang@google.com</span>
            <span>Phone: 123-456-7890</span>
          </ProfileDetails>
          <Button className="pointBu01">Save</Button>
        </div>
      </Container>
    </div>
  );
};

export default MyPage;
