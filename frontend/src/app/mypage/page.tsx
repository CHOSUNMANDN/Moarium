'use client';
import React, { useState, useEffect } from 'react';
import SmallHeader from '../../atoms/molecule/SmallHeader';
import SubmitButton from '../../atoms/atom/large-button';

// 박승찬 추가
import { axAuth } from '@/apis/axiosinstance';
import { userToken, isNuriKing } from '../../states/index';
import { useRouter } from 'next/navigation';
import { useRecoilState } from 'recoil';
import Navigation from '../../atoms/template/navigation';
import { hasNotToken } from '@/utils/validate/ExistenceChecker';
import { replaceRouterInitialize, replaceRouterPassword } from '@/utils/RouteHandling';
import NavigationFooter from '@/atoms/molecule/navigation-footer';

export default function SignUp() {
  const router = useRouter();
  const [token, setToken] = useRecoilState(userToken);
  const [isKing, setISKing] = useRecoilState(isNuriKing);
  const [userData, setUserData] = useState({
    name: '',
    phoneNum: '',
    major: '',
    gpa: '',
    address: '',
    specialtySkill: '',
    hobby: '',
    mbti: '',
    studentId: '',
    birthDate: '',
    advantages: '',
    disadvantage: '',
    selfIntroduction: '',
    photo: '',
    yearOfRegistration: '',
    email: '',
  });

  useEffect(() => {
    // 토큰이 없을시 초기화면으로 이동
    if (hasNotToken(token)) {
      replaceRouterInitialize(router);
    }

    // 해당 유저가 아니면 페이지 접근 불가능
  }, []);

  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: '/mypage',
    }).then(res => {
      const data = res.data;
      setUserData(data);
    });
  }, []);

  const isLoginAndSignupButton = false;
  return (
    <main>
      <div className="mb-10">
        <header>
          <Header isVisible={isLoginAndSignupButton} />
        </header>
        <article className="px-[7.5%]">
          <div className="mypage-content-one">
            <div className="text-3xl font-extrabold">내 정보</div>
            <div className="flex items-center">
              <div className="mr-3 bg-black w-20 h-20 rounded-full"></div>
              <div className="font-bold">
                <div>
                  이름(학번): {userData.name} ({userData.studentId})
                </div>
                <div>
                  학과(학점): {userData.major} ({userData.gpa})
                </div>
                <div>연락처: {userData.phoneNum}</div>
                <div>주소: {userData.address}</div>
              </div>
            </div>
          </div>
          <div>
            <div className="mt-2">MBTI: {userData.mbti}</div>
            <div className="mt-2">특기: {userData.specialtySkill}</div>
            <div className="mt-2">생년월일: {userData.birthDate}</div>
            <div className="mt-2">취미: {userData.hobby}</div>
            <div className="mt-2">장점: {userData.advantages}</div>
            <div className="mt-2">단점: {userData.disadvantage}</div>
            <div className="mt-2">자기소개: {userData.selfIntroduction}</div>

            <a onClick={() => router.push('/mypage/update')}>
              <SubmitButton text={'나의 페이지 수정하기'} addClass="text-2xl mt-5" />
            </a>
            <a onClick={() => replaceRouterPassword(router)}>
              <SubmitButton text={'비밀번호 수정하기'} addClass="text-2xl mt-5" />
            </a>
          </div>
        </article>
        <div className="mt-[3rem]">
          <div>
            <SubmitButton text={'로그아웃'} addClass={'bg-red mb-5'} />
          </div>
        </div>
      </div>

      <NavigationFooter isKing={isKing}></NavigationFooter>
    </main>
  );
}
