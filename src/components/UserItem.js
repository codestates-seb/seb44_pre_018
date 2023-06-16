import styled from 'styled-components';
import profile from '../components/assets/profile_image1.jpeg';
import TagList from './global/TagList';
import logo from '../components/assets/login_logo.png';

const UserWrap = styled.ul`
  display: grid;
  grid-gap: 10px;
  gap: 10px;
  grid-template-columns: repeat(3, 1fr);
  margin: 20px 0;

  > li {
    position: relative;
    transition: all 0.3s;
    border: 1.5px solid rgba(0, 0, 0, 0.3);
    border-radius: 5px;
    padding: 15px;
    box-sizing: border-box;
    display: flex;
    flex-direction: row;
    align-items: center;
    cursor: pointer;
    &:hover {
      border: 1.5px solid rgba(43, 102, 171, 1);
      .logoImg {
        opacity: 0.8;
      }
    }
    .userImgWrap {
      width: 70px;
      height: 70px;
      border-radius: 100%;
      overflow: hidden;
      margin-right: 15px;
    }
    .logoImg {
      transition: all 0.2s;
      position: absolute;
      right: 20px;
      top: 20%;
      z-index: -1;
      width: 35px;
      opacity: 0.5;
    }
    .userInfo {
      > p {
        font-size: 14px;
      }
      > ol {
        margin: 5px 0 10px;
        li {
          font-size: 11px;
          line-height: 15px;
        }
      }
    }
  }
`;

const UserItem = () => {
  return (
    <UserWrap>
      <li>
        <div className="userImgWrap">
          <img src={profile} alt="profile" className="profile-img" />
        </div>
        <div className="userInfo">
          <p>장근실</p>
          <ol>
            <li>질문수 : 1개</li>
            <li>답변수 : 10개</li>
          </ol>
          <TagList />
          <img src={logo} alt="profile" className="logoImg" />
        </div>
      </li>
    </UserWrap>
  );
};

export default UserItem;
