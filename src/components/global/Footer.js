import styled from 'styled-components';

const FooterContainer = styled.div`
  background-color: rgba(130, 130, 170, 0.12);
  left: 0;
  bottom: 0;
  width: 100%;
  height: 19vh;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  img {
    width: 8vw;
    height: 13vh;

    @media (max-width: 768px) {
      width: 5vw;
      height: 10vh;
    }
  }

  p {
    font-size: 14px;
    color: #b8b8b8;
    margin-top: 8px;

    @media (max-width: 768px) {
      font-size: 12px;
    }
  }
`;

const Footer = () => {
  return (
    <FooterContainer>
      <img src={require('assets/logo-h.png')} alt="logo2" />
      <p>Site desing / logo @ 2023 CodeStates_44_E1I5</p>
    </FooterContainer>
  );
};

export default Footer;
