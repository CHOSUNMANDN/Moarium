'use client';
import Header from '@/atoms/molecule/header';
import { MODAL_TITLE_DANGER, MODAL_TITLE_SUCCESS } from '@/utils/constans/modalTitle';

import React, { useState, useEffect } from 'react';
import CommonAltertModal from '@/atoms/atom/common-allert-modal';
import { checkHttpStatus } from '@/utils/validate/httpStatusChecker';
import Button from '../../../../atoms/atom/middle-button';
import { axBase } from '@/apis/axiosinstance';

export default function Login() {
  const [isModalOpen, setModalOpen] = useState(false);
  const [httpStatusMessage, setHttpStatusMessage] = useState('');
  const [username, setUserName] = useState('');
  const [authenticationEmail, setAuthenticationEmail] = useState('');
  const [email, setEmail] = useState('');
  const [message, setMessage] = useState('');

  const inputUserName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUserName(e.target.value);
  };

  const inputAuthenticationEmail = (e: React.ChangeEvent<HTMLInputElement>) => {
    setAuthenticationEmail(e.target.value);
  };

  const inputEmail = (e: React.ChangeEvent<HTMLInputElement>) => {
    setEmail(e.target.value);
  };

  useEffect(() => {
    if (isModalOpen === true) {
      setTimeout(() => {
        setModalOpen(false); // 2초 후에 AllertModal 닫기
      }, 3000);
    }
  }, [isModalOpen]);

  const sendEmail = () => {
    axBase()({
      method: 'post',
      url: '/admin/find/password',
      data: {
        name: username,
        email: email,
        authenticationEmail: authenticationEmail,
      },
    })
      .then(response => {
        setModalOpen(true);
        setMessage(response.data.message);
        setHttpStatusMessage(response.data.httpStatus);
      })
      .catch(err => {
        setModalOpen(true);
        setMessage(err.response.data.message);
        setHttpStatusMessage(err.response.data.httpStatus);
      });
  };

  return (
    <main>
      {isModalOpen ? <CommonAltertModal title={checkHttpStatus(httpStatusMessage) ? MODAL_TITLE_SUCCESS : MODAL_TITLE_DANGER} context={message} type={httpStatusMessage} /> : null}
      <header>
        <Header isVisible={false} />
      </header>
      <article className="flex flex-col items-center">
        <p className="font-bold text-5xl my-[4rem]">비밀번호 찾기</p>
        <input className="border-b-2 w-[85%] mb-[3rem]" type="text" name="name" placeholder="NAME(등록된 이름)" onChange={inputUserName} />
        <input className="border-b-2 w-[85%] mb-[3rem]" type="text" name="email" placeholder="E_MAIL" onChange={inputEmail} />
        <input className="border-b-2 w-[85%] mb-[5rem]" type="text" name="authenticationEmail" placeholder="E_MAIL(비밀번호 발급 원하는 이메일)" onChange={inputAuthenticationEmail} />
      </article>
      <div className="flex justify-center mb-[1rem]" onClick={sendEmail}>
        <Button text={'이메일 보내기'} addClass="text-2xl" />
      </div>
    </main>
  );
}
