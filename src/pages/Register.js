import { Link } from 'react-router-dom';
import { useEffect, useRef, useState } from 'react';
import axios from 'axios';
import LoginGoogle from 'components/global/login/LoginGoogle';
import LoginGithub from 'components/global/login/LoginGithub';

const Register = () => {
  const nameRef = useRef(null);
  const phonRef = useRef(null);
  const emailRef = useRef(null);
  const passwordRef = useRef(null);
  const [checkName, setCheckName] = useState(false);
  const [checkPhone, setCheckPhone] = useState(false);
  const [inputphoneValue, setInputPhoneValue] = useState('');
  const [checkEmail, setCheckEmail] = useState(false);
  const [checkEmail2, setCheckEmail2] = useState(false);
  const emailRegEx =
    /^[A-Za-z0-9]([-_.]?[A-Za-z0-9])*@[A-Za-z0-9]([-_.]?[A-Za-z0-9])*\.[A-Za-z]{2,3}$/i;
  const [checkPassword, setCheckPassword] = useState(false);

  const nameChange = (e) => {
    setCheckName(false);
  };
  const phoneChange = (e) => {
    setCheckPhone(false);
    const regex = /^[0-9\b -]{0,13}$/;
    if (regex.test(e.target.value)) {
      setInputPhoneValue(e.target.value);
    }
  };
  const emailChange = (e) => {
    setCheckEmail(false);
    if (!emailRegEx.test(emailRef.current.value)) {
      setCheckEmail2(true);
    } else {
      setCheckEmail2(false);
    }
  };
  const passwordChange = (e) => {
    setCheckPassword(false);
  };

  const submitForm = async (e) => {
    e.preventDefault();
    // console.log(nameRef.current.value);
    const nameValue = nameRef.current.value;
    const phoneValue = phonRef.current.value;
    const emailValue = emailRef.current.value;
    const passwordValue = passwordRef.current.value;
    setCheckName(nameValue === '');
    setCheckPhone(phoneValue === '');
    setCheckEmail(emailValue === '');
    setCheckPassword(passwordValue === '');
    try {
      const result = await axios.post('/member/register', {
        name: nameValue,
        phone: phoneValue,
        email: emailValue,
        password: passwordValue,
      });
      //회원가입 성공 여부만 알려줄때
      navigate('/login');
      //멤버 번호 알려줄때
      // => 로그인시 멤버 정보를 기반으로 회원정보 get하는 로직 활용
    } catch (err) {
      console.log(err);
      alert(err.response.data);
    }
  };

  useEffect(() => {
    if (inputphoneValue.length === 10) {
      setInputPhoneValue(
        inputphoneValue.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3')
      );
    }
    if (inputphoneValue.length === 13) {
      setInputPhoneValue(
        inputphoneValue
          .replace(/-/g, '')
          .replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3')
      );
    }
  }, [inputphoneValue]);

  return (
    <div className="flex flex-col items-center w-[26rem]">
      <form className="LoginForm" onSubmit={submitForm}>
        <label className="labelText pt-0" htmlFor="name">
          Display name
        </label>
        <input
          ref={nameRef}
          type="text"
          id="name"
          onChange={nameChange}
          className={`inputBox ${checkName && 'disabledInput'}`}
        />
        {checkName && <p className="notice">이름은 필수 정보입니다.</p>}

        <label className="labelText" htmlFor="phone">
          Phone
        </label>
        <input
          ref={phonRef}
          type="text"
          id="phone"
          value={inputphoneValue}
          onChange={phoneChange}
          className={`inputBox ${checkPhone && 'disabledInput'}`}
        />
        {checkPhone && <p className="notice">전화번호는 필수 정보입니다.</p>}

        <label className="labelText" htmlFor="email">
          Email
        </label>
        <input
          ref={emailRef}
          type="text"
          id="email"
          onChange={emailChange}
          className={`inputBox ${checkEmail && 'disabledInput'}`}
        />
        {checkEmail && <p className="notice">이메일은 필수 정보입니다.</p>}
        {checkEmail2 && <p className="notice">이메일 정확히 입력하세요.</p>}

        <label className="labelText" htmlFor="password">
          Password
        </label>
        <input
          ref={passwordRef}
          type="password"
          id="password"
          onChange={passwordChange}
          className={`inputBox ${checkPassword && 'disabledInput'}`}
        />
        {checkPassword && (
          <p className="notice">비밀번호는 필수 입력해야 합니다.</p>
        )}

        <button type="submit" className="pointBu02 w-full mt-10">
          Sign up
        </button>
      </form>
      <p className="mb-7  sm:text-sm">
        <span>Already have an account?</span>
        <Link
          className="transition ml-2 text-pointCol05 border-b-[1px] border-pointCol05 hover:text-pointCol02 hover:border-pointCol02"
          to="/Login"
        >
          Log in
        </Link>
      </p>
      <LoginGoogle />
      <LoginGithub />
    </div>
  );
};

export default Register;
