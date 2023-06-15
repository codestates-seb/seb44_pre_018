import logo2 from '../assets/logo-h.png';

const Footer = () => {
  return (
    <div className="footer-container">
      <div
        className="h-25 flex justify-center"
        style={{
          backgroundColor: 'rgba(130, 130, 170, 0.12)',
          position: 'absolute',
          bottom: 0,
          left: 0,
          width: '100%',
        }}
      >
        <div className="flex flex-col items-center">
          <img src={logo2} alt="logo2" className="h-15" />
          <p className="text-sm font-light py-2" style={{ color: '#B8B8B8' }}>
            Site desing / logo @ 2023 CodeStates_44_E1I5
          </p>
        </div>
      </div>
    </div>
  );
};

export default Footer;
