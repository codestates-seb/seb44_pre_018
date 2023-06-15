const Footer = () => {
  return (
    <div className="footer-container">
      <div
        className="h-26 flex justify-center items-center"
        style={{
          backgroundColor: 'rgba(130, 130, 170, 0.12)',
          position: 'absolute',
          bottom: 0,
          left: 0,
          width: '100%',
        }}
      >
        <div className="flex flex-col items-center">
          <img
            src={process.env.PUBLIC_URL + '/logo-h.png'}
            alt="로고"
            className="h-15 mr-5"
          />
          <p style={{ color: '#B8B8B8' }}>
            Site desing / logo @ 2023 CodeStates_44_E1I5
          </p>
        </div>
      </div>
    </div>
  );
};

export default Footer;
