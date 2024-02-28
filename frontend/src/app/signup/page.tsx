'use client';
import { axBase } from '@/apis/axiosinstance';
import React, { useState, useEffect } from 'react';
import Header from '../../atoms/molecule/header';
import Input from '../../atoms/atom/input-form';
import Textarea from '../../atoms/atom/text-area-form';
import SubmitButton from '../../atoms/atom/large-button';
import AllertModal from '../../atoms/atom/allert-modal';
import { useRouter } from 'next/navigation';
import { hasToken } from '@/utils/validate/ExistenceChecker';
import { replaceRouterMain } from '@/utils/RouteHandling';
import { useRecoilState } from 'recoil';
import { userToken } from '../../states/index';
import { goToPageTop } from '@/utils/windowScrollUtils';

export default function SignUp() {
  const router = useRouter();
  const [AllertModalstatus, setAllertModalStatus] = useState(0);
  const [token, setToken] = useRecoilState(userToken);

  useEffect(() => {
    // 토큰이 있을시 메인화면 이동
    if (hasToken(token)) {
      replaceRouterMain(router);
    }
  }, []);

  useEffect(() => {
    // 휴가 신청 모달창
    if (AllertModalstatus !== 0) {
      goToPageTop(); // Scroll to the top of the page
      setTimeout(() => {
        setAllertModalStatus(0); // 5초 후에 AllertModal 닫기
        router.replace('/');
      }, 5000);
    }
  }, [AllertModalstatus]);

  const textOfAllert = [
    // 휴가 신청 모달창 텍스트 입력
    { title: '지원 완료', context: '누리 고시원 실장분께 연락 주세요, 5초후에 메이페이지로 이동합니다.', type: 'success' },
    { title: '지원 할수 없습니다.', context: '누리 고시원 실장분께 연락 주세요', type: 'danger' },
  ];

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
    photo: '사진',
    email: '',
    password: '',
    passwordConfirm: '',
  });

  const validation = () => {
    const passwordvalidation = /^(?=.*[A-Za-z])(?=.*\d)(?=.*[$@$!%*#?&])[A-Za-z\d$@$!%*#?&]{8,}$/;
    const phonenumbervalidation = /^01(?:0|1|[6-9])-(?:\d{3}|\d{4})-\d{4}$/;
    if (!userData.name) {
      alert('성함을 확인해주세요.');
    } else if (!userData.phoneNum || !phonenumbervalidation.test(userData.phoneNum)) {
      alert('핸드폰 번호를 확인해주세요.');
    } else if (!userData.major) {
      alert('전공을 확인해주세요.');
    } else if (!userData.gpa) {
      alert('학점을 확인해주세요.');
    } else if (!userData.address) {
      alert('주소를 확인해주세요.');
    } else if (!userData.specialtySkill) {
      alert('특기를 확인해주세요.');
    } else if (!userData.hobby) {
      alert('취미를 확인해주세요.');
    } else if (!userData.mbti) {
      alert('mbti를 확인해주세요.');
    } else if (!userData.studentId) {
      alert('학번을 확인해주세요.');
    } else if (!userData.birthDate) {
      alert('생년월일을 확인해주세요.');
    } else if (userData.advantages.length < 100) {
      alert('장점을 100자 이상 입력해주세요.');
    } else if (userData.disadvantage.length < 100) {
      alert('단점을 100자 이상 입력해주세요.');
    } else if (userData.selfIntroduction.length < 200) {
      alert('자기소개를 200자 이상 입력해주세요.');
    } else if (!userData.email) {
      alert('이메일을 확인해주세요.');
    } else if (!userData.password) {
      alert('비밀번호를 확인해주세요.');
    } else if (userData.password !== userData.passwordConfirm || !passwordvalidation.test(userData.password)) {
      alert('비밀번호를 다시 확인해주세요.');
    } else {
      submitUserData();
    }
  };

  const submitUserData = () => {
    axBase()({
      method: 'post',
      url: '/sign',
      data: {
        name: userData.name,
        phoneNum: userData.phoneNum,
        major: userData.major,
        gpa: userData.gpa,
        address: userData.address,
        specialtySkill: userData.specialtySkill,
        hobby: userData.hobby,
        mbti: userData.mbti,
        studentId: userData.studentId,
        birthDate: userData.birthDate,
        advantages: userData.advantages,
        disadvantage: userData.disadvantage,
        selfIntroduction: userData.selfIntroduction,
        photo: userData.photo,
        email: userData.email,
        password: userData.password,
      },
    })
      .then(res => {
        const isApply = res.data.isApply;

        // 0이 닫기, 1이 성공, 2가 실패
        if (isApply) {
          setAllertModalStatus(1);
        } else {
          setAllertModalStatus(2);
        }
      })
      .catch(err => {
        console.log(err);
      });
  };

  return (
    <main>
      {AllertModalstatus !== 0 ? (
        <AllertModal title={textOfAllert[AllertModalstatus - 1].title} context={textOfAllert[AllertModalstatus - 1].context} type={textOfAllert[AllertModalstatus - 1].type} />
      ) : null}
      <header>
        <Header isVisible={false} />
      </header>
      <article className="px-[7.5%]">
        <Input title={'성함'} userData={userData} setUserData={setUserData} dataname="name" />
        <Input title={"핸드폰 번호('-' 포함)"} userData={userData} setUserData={setUserData} dataname="phoneNum" />
        <Input title={'전공'} userData={userData} setUserData={setUserData} dataname="major" />
        <Input title={'학점'} userData={userData} setUserData={setUserData} dataname="gpa" />
        <Input title={'주소'} userData={userData} setUserData={setUserData} dataname="address" />
        <Input title={'특기'} userData={userData} setUserData={setUserData} dataname="specialtySkill" />
        <Input title={'취미'} userData={userData} setUserData={setUserData} dataname="hobby" />
        <Input title={'MBTI'} userData={userData} setUserData={setUserData} dataname="mbti" />
        <Input title={'학번'} userData={userData} setUserData={setUserData} dataname="studentId" />
        <Input title={'생년월일'} userData={userData} setUserData={setUserData} dataname="birthDate" />
        <Textarea title={'장점(100자 이상)'} userData={userData} setUserData={setUserData} dataname="advantages" />
        <Textarea title={'단점(100자 이상)'} userData={userData} setUserData={setUserData} dataname="disadvantage" />
        <Textarea title={'자기소개(200자 이상)'} userData={userData} setUserData={setUserData} dataname="selfIntroduction" />
        <Input title={'이메일'} userData={userData} setUserData={setUserData} dataname="email" />
        <Input title={'비밀번호( 8자 이상, 특수문자, 숫자, 영문자 포함)'} type={'password'} userData={userData} setUserData={setUserData} dataname="password" />
        <Input title={'비밀번호 확인'} type={'password'} userData={userData} setUserData={setUserData} dataname="passwordConfirm" />
        {userData.password !== userData.passwordConfirm ? <span className="text-[#FF0A0A]">비밀번호와 일치하지 않습니다.</span> : <span className="text-blue">비밀번호가 일치합니다.</span>}
      </article>
      <footer className="flex justify-center px-[7.5%] mt-[2rem] mb-[3rem]">
        <div className="w-[100%]" onClick={validation}>
          <SubmitButton text={'신청하기'} addClass="text-2xl" />
        </div>
      </footer>
    </main>
  );
}
