import { useEffect, useState } from 'react';
import axios from 'axios';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faArrowUpFromBracket } from '@fortawesome/free-solid-svg-icons';
import styled from 'styled-components';
import { useSelector } from 'react-redux';
import { useNavigate } from 'react-router';
// import { useDispatch } from 'react-redux';

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
    padding-bottom: 5px;
  }

  input {
    background-color: #fff;
    padding-bottom: 5px;
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
  const navigate = useNavigate();
  // const dispatch = useDispatch();
  const { user } = useSelector((state) => state);
  // console.log('user:', user);
  const [thisUser, setThisUser] = useState({
    ...user.data,
    currentPassword: '',
    password: '',
  });
  const [previewImage, setPreviewImage] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [imageFile, setImageFile] = useState(null);
  useEffect(() => {
    setThisUser({ ...user, currentPassword: '', password: '' });
  }, [user]);

  // const profileImageURL = user.data.profileImagePath;

  const changePicture = () => {
    const inputElement = document.createElement('input');
    const acceptStr = '.jpg,.png,.jpeg';
    inputElement.type = 'file';
    inputElement.accept = acceptStr;

    inputElement.addEventListener('change', async (event) => {
      const file = event.target.files[0];
      const fileExtension = '.' + file.name.split('.').pop();

      if (acceptStr.includes(fileExtension)) {
        setImageFile(file);

        const reader = new FileReader();
        reader.readAsDataURL(file); 
        reader.onloadend = () => {
          const base64 = reader.result;
          const userObj = {
            profile_image: base64,
          };
          localStorage.setItem('member', JSON.stringify(userObj));
          setPreviewImage(base64);
          setImageFile(file);
        };
      }
    });

    inputElement.click();
  };

  const handleInputChange = (e) => {
    // const value = e.target.value;
    // const name = e.target.name;
    const {
      target: { value, name },
    } = e;
    setThisUser((prevMember) => ({
      ...prevMember,
      [name]: value,
    }));
  };

  const saveChanges = async () => {
    console.log('thisUser:', thisUser);
    try {
      //1. 로그인 로직을 이용해서 기존 패스워드로 로그인이 가능한지
      // const canLogin = await axios.post('/auth/login', {
      //   username: thisUser.email,
      //   password: thisUser.currentPassword,
      // });
      // 성공하면 아마 아래로 순차진행이 될 것이고
      // 실패하면 아마 catch문으로 날라갈거다. => 그에 따른 로직 추가하면 됨. 예를들면 alert("유저 정보 변경 실패");
      // 유저정보 패치 보낸다.
      const result1 = await axios.patch(
        '/member/update',
        {
          name: thisUser.data.name,
          phone: thisUser.data.phone,
          password: thisUser.currentPassword,
          //  email: thisUser.data.email,
          //  profileImageName: thisUser.data.profileImageName,
          //  profileImagePath: thisUser.data.profileImagePath,
        },
        {
          headers: {
            Authorization: thisUser.token,
            'ngrok-skip-browser-warning': 'true',
          },
        }
      );
      console.log('result1:', result1);
      // 유저이미지 패치 보낸다.
      const formData = new FormData();
      if (imageFile) {
        formData.append('file', imageFile);
      }

      const result2 = await axios.patch('/member/upload', formData, {
        headers: {
          Authorization: thisUser.token,
          'ngrok-skip-browser-warning': 'true',
          'Content-Type': 'multipart/form-data', 
        },
      });
      console.log('result2:',result2);

      if (result2.status === 200 && result2.data.profileImageName && result2.data.profileImagePath) {
        // dispatch(loginUser({
        //   ...thisUser,
        //   profileImageName: result2.data.profileImageName,
        //   profileImagePath: result2.data.profileImagePath,
        // }));
        setThisUser((prevUser) => ({
          ...prevUser,
          profileImageName: result2.data.profileImageName,
          profileImagePath: result2.data.profileImagePath,
        }));
        setPreviewImage(base64);
      }
      console.log('user2:', user);
    } catch (err) {
      console.log('err', err);
    }
    // setMember((prevMember) =>
    //   editedMember.profile_image ? editedMember : { ...prevMember }
    // );
    // setEditedMember(null);
    // setIsEditing(false);
  };

  const editProfile = () => {
    setIsEditing(true);
  };
  const checkLogin = () => {
    if (!user.isLogin) {
      alert('로그인이 필요합니다.', (location.href = '/'));
      return <></>;
    }
  };
  useEffect(() => {
    checkLogin();
  }, [user]);
  return (
    <div className="inner">
      <h3 className="maintitle">MyPage</h3>
      <h1 className="mt-3">Hello, {user.data.name}!</h1>
      <Container>
        <div className="left-section">
          <img
            src={
              previewImage
                ? previewImage || user.profile_image
                : require('assets/profile_image1.jpeg')
            }
            alt="Profile"
          />
          {isEditing ? (
            <Button className="pointBu01 w-50 text-sm" onClick={changePicture}>
              <FontAwesomeIcon icon={faArrowUpFromBracket} className="mr-1" />
              Change Picture
            </Button>
          ) : (
            <Button className="pointBu03" onClick={editProfile}>
              Edit Profile
            </Button>
          )}
        </div>
        <div className="light-section">
          <ProfileDetails>
            <span>
              Name:
              <input
                type="text"
                name="name"
                value={thisUser.name}
                onChange={handleInputChange}
                disabled={!isEditing}
              />
            </span>
            <span>
              Email:
              <input
                type="text"
                name="email"
                value={thisUser.email}
                onChange={handleInputChange}
                disabled={!isEditing}
              />
            </span>
            <span>
              기존 Password:
              <input
                type="password"
                name="currentPassword"
                value={thisUser.currentPassword}
                onChange={handleInputChange}
                disabled={!isEditing}
              />
            </span>
            <span>
              새로운 Password:
              <input
                type="password"
                name="password"
                value={thisUser.password}
                onChange={handleInputChange}
                disabled={!isEditing}
              />
            </span>
            <span>
              Phone:
              <input
                type="text"
                name="phone"
                value={thisUser.phone}
                onChange={handleInputChange}
                disabled={!isEditing}
              />
            </span>
          </ProfileDetails>
          {isEditing ? (
            <Button className="pointBu01" onClick={saveChanges}>
              Save
            </Button>
          ) : null}
        </div>
      </Container>
    </div>
  );
};

export default MyPage;
