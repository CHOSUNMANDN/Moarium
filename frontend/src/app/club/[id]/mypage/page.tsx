'use client';
import React, { useState, useEffect } from 'react';
import Header from '../../atoms/molecule/header';
import SubmitButton from '../../atoms/atom/large-button';
import MyPageButton from '@/atoms/atom/mypage_list';

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
  const [member_id, setMember_id] = useState({
    id : '3'
  });

  const [userData, setUserData] = useState({
    message: '',
    httpStatus: '',
    stateCode: '',
    timestamp: '',
    result : {
        userProfile : '',
        userName : '이한빈',
        userStudentId : '18학번',
        userClub : '누리고시원',
        userStatusMessage : '작성된 글이 없습니다.' 
    }
  });

  const [NoUserData, setNoUserData] = useState({
    message: '',
    httpStatus: '',
    stateCode: '',
    timestamp: '',
  });

  useEffect(() => {
    // 토큰이 없을시 초기화면으로 이동
    /*if (hasNotToken(token)) {
      replaceRouterInitialize(router);
    }*/

    // 해당 유저가 아니면 페이지 접근 불가능
  }, []);

  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: '/mypage',
    }).then(res => {   // 성공했을 시, then 이하 실행 / catch는 실패했을 시
      const data = res.data;
      setMember_id(data);
      setUserData(data);
    })
    // .catch(){
    //   setNoUserData(data);
    // })
  }, []);

  const isLoginAndSignupButton = false;
  return (
    <main>
      <div className="mt-10">
        <header>
          <Header isVisible={isLoginAndSignupButton} />
        </header>
        <article className="px-[4.5%]">
          <div className="mypage-content-one">
            <div className="text-3xl font-bold">마이페이지</div>
            <div>
              <div className="font-bold mt-4 border-grey bg-white w-[7rem] h-[7rem] border-2 rounded-3xl">
                {/* <img src="Images/emptyprofile.png" alt=""></img> */}
                <div className="text-navy w-[5rem] ml-[8rem]">소속 동아리</div>
                <div className="border-grey border bg-lightest-grey ml-[7.65rem] w-[12.7rem] h-[1.75rem] align-left rounded-lg">
                  <div className="text-black font-regular mt-[0.05rem] w-[5rem] ml-[0.45rem]">{userData.result.userClub}</div>
                </div>                
                  <div className="text-navy w-[5rem] mt-2 ml-[8rem]">상태메세지</div>
                <div className="border-grey border bg-lightest-grey ml-[7.65rem] w-[12.7rem] h-[7rem] align-left rounded-lg">
                  <div className='text-grey font-regular mt-[0.1rem] ml-[0.4rem]'>{userData.result.userStatusMessage}</div>
                </div>
              </div>
              </div>
              
              <div className="text-black font-bold text-2xl mt-[0.3rem] w-[4.5rem] h-[1.6rem] ml-6">
                  {userData.result.userName}
                </div>
                <div className="text-dark-grey font-bold mt-[0.1rem] text-l w-[3rem] ml-8">
                  {userData.result.userStudentId}
                </div>
          </div>
          <div>
              <div className="grid-cols-1 devide-y mt-[3rem] bg-blue border-grey border text-xl rounded-lg">
              <div>
                {/* <img src="Images/postcheck.png"></img> */}
                <a onClick={() => router.push('')}>
                    <MyPageButton text={'내가 쓴 글 보기'} addClass="text-xl mt-4"/>
                </a>
              </div>  
                <a onClick={() => router.push('')}><MyPageButton text={'내가 쓴 댓글 보기'} addClass="text-xl mt-0"/></a>
                <a onClick={() => router.push('')}><MyPageButton text={'나의 출석 현황'} addClass="text-xl mt-0"/></a>
                <a onClick={() => router.push('')}><MyPageButton text={'휴면 전환하기'} addClass="text-xl mt-0"/></a>
                <a onClick={() => router.push('')}><MyPageButton text={'동아리 탈퇴하기'} addClass="text-xl mt-0"/></a>
              </div>
          </div>
        </article>
      </div>
      <NavigationFooter isKing={isKing}></NavigationFooter>
    </main>
  );
}
