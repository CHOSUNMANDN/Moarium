'use client';
import Header from '../../../atoms/molecule/header';
import { useEffect, useState } from 'react';
import { userToken } from '../../../states/index';
import { useRecoilState } from 'recoil';
import { replaceRouterMain } from '@/utils/RouteHandling';
import { hasToken } from '@/utils/validate/ExistenceChecker';
import GoogleLoginButton from '@/apis/GoogleLogin';
import Image from 'next/image';
import { axBase } from '@/apis/axiosinstance';
import { useRouter } from 'next/navigation';
import Input from '../../../atoms/atom/input-form-value';
import SmallButton from '@/atoms/atom/thin-long-button';

interface LoginFirstType {
  name: string;
  nickName: string;
  email: string;
  major: string;
  studentId: string;
}

interface CertificationDataType {
  nickName: string;
  email: string;
}

export default function Home() {
  const [token, setToken] = useRecoilState(userToken);
  const router = useRouter();

  useEffect(() => {
    // 토큰이 있을시 메인페이지 이동
    if (hasToken(token)) {
      replaceRouterMain(router);
    }
  }, []);

  const [userData, setUserData] = useState<LoginFirstType>({
    name: '',
    nickName: '',
    email: '',
    major: '',
    studentId: '',
  });

  const [certificationData, setCertificationData] = useState<CertificationDataType>({
    nickName: '',
    email: '',
  });

  const login = () => {
    axBase()({
      method: 'post',
      // url: '/users/login/google',
      url: '/users/login/google/first',
      data: {
        name: userData.name,
        nickName: userData.nickName,
        email: userData.email,
        major: userData.major,
        studentId: userData.studentId,
      },
    })
      .then(res => {
        // setToken(res.data.result.accessToken);
        console.log(res.data.result);
      })
      .catch(err => {
        console.log(err);
      });
  };
  // const [certificationData, setCertificationData] = useState<CertificationDataType>({

  return (
    <>
      <header>
        <Header isVisible={true} />
      </header>
      <main>
        <article className="px-[7.5%]">
          <Input title={'이름'} userData={userData} setUserData={setUserData} dataname="name" value={userData.name} />
          <div>
            <Input title={'닉네임'} userData={userData} setUserData={setUserData} dataname="nickName" value={userData.nickName} />
            <SmallButton text={'중복 확인'} />
          </div>
          <div>
            <Input title={'이메일'} userData={certificationData} setUserData={setUserData} dataname="email" value={certificationData.email} />
            <SmallButton text={'이메일 확인'} />
          </div>
          <div>
            <Input title={'이메일 인증'} userData={userData} setUserData={setUserData} dataname="email" value={certificationData.email} />
            <SmallButton text={'인증'} />
          </div>
          <Input title={'전공'} userData={userData} setUserData={setUserData} dataname="major" value={userData.major} />
          <Input title={'학번'} userData={userData} setUserData={setUserData} dataname="studentId" value={userData.studentId} />
        </article>
        <button className="bg-auto bg-no-repeat w-[179px] h-[40px]" onClick={login}>
          테스트 로그인 버튼
        </button>
      </main>
    </>
  );
}
