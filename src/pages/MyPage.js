import { useEffect, useState } from 'react';
import axios from 'axios';
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
  const [member, setMember] = useState(null);
  const [editedMember, setEditedMember] = useState(null);
  const [previewImage, setPreviewImage] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  
  
  useEffect(() => {
    const fetchMemberData = async () => {
      try {
        const response = await axios.get('/data/member.json');
        const data = response.data;
        const storedMember = JSON.parse(localStorage.getItem('member'));

        if (storedMember) {
          setMember(storedMember);
          setEditedMember(storedMember);
          setPreviewImage(storedMember.profile_image);
        } else {
          setMember(data.member[0]);
          setEditedMember(data.member[0]);
          setPreviewImage(data.member[0].profile_image);
        }
      } catch (error) {
        console.error('Error fetching member data:', error);
      }
    };

    fetchMemberData();
  }, []);

  useEffect(() => {
    if (editedMember) {
      localStorage.setItem('member', JSON.stringify(editedMember));
    }
  }, [editedMember]);

  useEffect(() => {
    setPreviewImage(editedMember?.profile_image || member?.profile_image);
  }, [editedMember, member]);

  if (!member) {
    return <div>Loading...</div>;
  }

  const changePicture = () => {
    const inputElement = document.createElement('input');
    inputElement.type = 'file';
    inputElement.accept = 'image/*';
    inputElement.addEventListener('change', async (event) => {
      const file = event.target.files[0];
      const reader = new FileReader();
      reader.onloadend = () => {
        setEditedMember((prevMember) => ({
          ...prevMember,
          profile_image: reader.result,
        }));
        setPreviewImage(reader.result);
      };
      reader.readAsDataURL(file);
    });
    inputElement.click();
  };

  const handleInputChange = (e, f) => {
    const value = e.target.value;
    setEditedMember((prevMember) => ({
      ...prevMember,
      [f]: value,
    }));
  };

  const saveChanges = () => {
    setMember((prevMember) =>
      editedMember.profile_image ? editedMember : { ...prevMember }
    );
    setEditedMember(null);
    setIsEditing(false);
    
  };

  const editProfile = () => {
    setEditedMember({ ...member });
    setIsEditing(true); 
  };

  
  return (
    <div className="inner">
      <h3 className="maintitle">MyPage</h3>
      <h1 className="mt-3">Hello, {member.name}!</h1>
      <Container>
        <div className="left-section">
          <img
            src={previewImage || member.profile_image}
            alt="Profile"
          />
          <Button 
            className="pointBu01 w-50 text-sm" 
            onClick={changePicture}
          >
            <FontAwesomeIcon 
              icon={faArrowUpFromBracket} 
              className="mr-1" 
            />
            Change Picture
          </Button>
          {isEditing ? null : (
            <Button 
              className="pointBu03" 
              onClick={editProfile}
            >
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
                value={isEditing ? editedMember.name : member.name}
                onChange={(event) => handleInputChange(event, 'name')}
                disabled={!isEditing}
              />
            </span>
            <span>
              Email:
              <input
                type="text"
                value={isEditing ? editedMember.email : member.email}
                onChange={(event) => handleInputChange(event, 'email')}
                disabled={!isEditing}
              />
            </span>
            <span>
              Phone:
              <input
                type="text"
                value={isEditing ? editedMember.phone : member.phone}
                onChange={(event) => handleInputChange(event, 'phone')}
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