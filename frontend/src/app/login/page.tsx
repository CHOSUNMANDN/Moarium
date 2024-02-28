'use client';
import React, { useState, useEffect } from 'react';
import Header from '../../atoms/molecule/header';
import Button from '../../atoms/atom/middle-button';
import { axBase } from '../../apis/axiosinstance';
import Link from 'next/link';
import { useRecoilState } from 'recoil';
import { userToken, isNuriKing } from '../../states/index';
import { useRouter } from 'next/navigation';
import Modal from '../../atoms/atom/allert-modal';
import { hasToken } from '@/utils/validate/ExistenceChecker';
import { replaceRouterFindEmail, replaceRouterFindPassword, replaceRouterMain } from '@/utils/RouteHandling';

export default function Login() {
  const [token, setToken] = useRecoilState(userToken);
  const [isKing, setIsNuriKing] = useRecoilState(isNuriKing);
  const [username, setUserName] = useState('');
  const [password, setPassword] = useState('');
  const router = useRouter();
  const [isModalOpen, setIsModalOpen] = useState(false);

  const inputUserName = (e: React.ChangeEvent<HTMLInputElement>) => {
    setUserName(e.target.value);
  };

  const inputPassword = (e: React.ChangeEvent<HTMLInputElement>) => {
    setPassword(e.target.value);
  };

  useEffect(() => {
    // 토큰이 있을시 메인 페이지로 이동
    if (hasToken(token)) {
      replaceRouterMain(router);
    }
  }, []);

  useEffect(() => {
    if (isModalOpen === true) {
      setTimeout(() => {
        setIsModalOpen(false); // 2초 후에 AllertModal 닫기
      }, 2000);
    }
  }, [isModalOpen]);

  const login = () => {
    axBase()({
      method: 'post',
      url: '/login',
      data: {
        email: username,
        password: password,
      },
    })
      .then(response => {
        setToken(response.data.accessToken);
        setIsNuriKing(response.data.nuriKing);
        router.replace('/main');
      })
      .catch(err => {
        setIsModalOpen(true);
        console.log(err);
      });
  };

  return (
    <main>
      {isModalOpen ? <Modal title={'틀렸습니다'} context={'아이디/비밀번호를 확인해주세요'} type={'danger'} /> : null}
      <header>
        <Header isVisible={false} />
      </header>
      <article className="flex flex-col items-center">
        <p className="font-bold text-5xl my-[6rem]">Login</p>
        <input className="border-b-2 w-[85%] mb-[3rem]" type="text" name="username" placeholder="  E_MAIL" onChange={inputUserName} />
        <input className="border-b-2 w-[85%] mb-[5rem]" type="password" name="password" placeholder="  PASSWORD" onChange={inputPassword} />
        <div className="mb-[1rem]" onClick={login}>
          <Button text={'로그인'} addClass="text-2xl" />
        </div>
        <p></p>
        <Link href={'/signup'}>
          <Button text={'지원서 작성'} addClass="text-2xl" />
        </Link>
      </article>
      <footer className="flex justify-center mt-[.5rem] mb-[1rem]">
        <div>
          <div>
            <a onClick={() => replaceRouterFindEmail(router)}>이메일을 잊어버리셨나요?</a>
          </div>
          <div>
            <a onClick={() => replaceRouterFindPassword(router)}>비밀번호를 잊어버리셨나요?</a>
          </div>
        </div>
      </footer>
    </main>
  );
}
