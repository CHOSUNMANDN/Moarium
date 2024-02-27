'use client';
import Header from '@/atoms/molecule/header';
import { MODAL_TITLE_DANGER, MODAL_TITLE_SUCCESS } from '@/utils/constans/modalTitle';

import React, { useState, useEffect } from 'react';
import CommonAltertModal from '@/atoms/atom/common-allert-modal';
import { checkHttpStatus } from '@/utils/validate/httpStatusChecker';
import Button from '../../../../atoms/atom/middle-button';
import { axAuth, axBase } from '@/apis/axiosinstance';
import { hasNotToken } from '@/utils/validate/ExistenceChecker';
import { replaceRouterInitialize } from '@/utils/RouteHandling';
import { useRecoilState } from 'recoil';
import { userToken } from '@/states';
import { useRouter } from 'next/navigation';

export default function Login() {
  const [isModalOpen, setModalOpen] = useState(false);
  const [httpStatusMessage, setHttpStatusMessage] = useState('');
  const [password, setPassword] = useState('');
  const [checkUpdatePassword, setCheckUpdatePassword] = useState('');
  const [updatePassword, setUpdatePassword] = useState('');
  const [message, setMessage] = useState('');
  const [token, setToken] = useRecoilState(userToken);
  const router = useRouter();

  useEffect(() => {
    // 토큰이 없을시 초기화면으로 이동
    if (hasNotToken(token)) {
      replaceRouterInitialize(router);
    }

    // 해당 유저가 아니면 페이지 접근 불가능
  }, []);

  const inputPassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  const inputCheckUpdatePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setCheckUpdatePassword(e.target.value);
  };

  const inputUpdatePassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUpdatePassword(e.target.value);
  };

  useEffect(() => {
    if (isModalOpen === true) {
      setTimeout(() => {
        setModalOpen(false); // 2초 후에 AllertModal 닫기
      }, 3000);
    }
  }, [isModalOpen]);

  const submitUpdatePassword = () => {
    axAuth(token)({
      method: 'post',
      url: '/admin/update/password',
      data: {
        password: password,
        updatePassword: updatePassword,
        checkUpdatePassword: checkUpdatePassword,
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
        <p className="font-bold text-5xl my-[4rem]">비밀번호 변경</p>
        <input className="border-b-2 w-[85%] mb-[3rem]" type="password" name="name" placeholder="PASSWORD(현제 PW)" onChange={inputPassword} />
        <input className="border-b-2 w-[85%] mb-[3rem]" type="password" name="updatePassword" placeholder="UPDATE PASSWORD(변경 PW)" onChange={inputUpdatePassword} />
        <input className="border-b-2 w-[85%] mb-[5rem]" type="password" name="checkUpdatePassword" placeholder="CHECK UPDATE PASSWORD" onChange={inputCheckUpdatePassword} />
      </article>
      <div className="flex justify-center mb-[1rem]" onClick={submitUpdatePassword}>
        <Button text={'변경하기'} addClass="text-2xl" />
      </div>
    </main>
  );
}
