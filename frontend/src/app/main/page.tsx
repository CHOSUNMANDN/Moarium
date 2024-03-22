'use client';
import { axAuth } from '@/apis/axiosinstance';
import SmallHeader from '../../atoms/molecule/SmallHeader';
import { useState, useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { userToken, isNuriKing } from '../../states/index';
import { useRouter } from 'next/navigation';
import Navigation from '../../atoms/template/navigation';
import Button from '../../atoms/atom/large-button';
import AttendanceModal from '../../atoms/molecule/attendance-modal';
import AllertModal from '../../atoms/atom/allert-modal';
import Graduater from '@/atoms/atom/graduater';
import CurrentMember from '@/atoms/molecule/CurrentMember';
import MemberInformationModal from '@/atoms/molecule/member-infromation-modal';
import { hasNotToken } from '@/utils/validate/ExistenceChecker';
import { AppRouterInstance } from 'next/dist/shared/lib/app-router-context';
import { replaceRouterInitialize } from '@/utils/RouteHandling';
import NavigationFooter from '@/atoms/molecule/navigation-footer';

interface userDataPropsTypeZero {
  cntVacation: number;
  name: string;
  userId: number;
  weeklyData: number[];
}

interface userDataPropsTypeOne {
  phoneNum: string;
  name: string;
  userId: number;
  yearOfRegistration: string;
}

export default function Main() {
  const router = useRouter();
  const [token, setToken] = useRecoilState(userToken);
  const [type, setType] = useState(0);
  const [userList, setUserList] = useState([]);
  const [isKing, setIsKing] = useRecoilState(isNuriKing);
  const [isAttendanceModalOpen, setIsAttendanceModalOpen] = useState(false);
  const [AllertModalstatus, setAllertModalStatus] = useState(0);
  const [isTodayAttendance, setIsTodayAttendance] = useState(false);
  const [isMemberInfoOpen, setIsMemberInfoOpen] = useState(0);

  const textOfAllert = [
    { title: '틀렸습니다!', context: '결석, 휴가 완료 또는 번호를 다시 확인해주세요', type: 'danger' },
    { title: '오류!', context: '인터넷 연결을 확인해주세요', type: 'danger' },
    { title: '출석이 완료되었습니다.', context: '', type: 'success' },
  ];

  useEffect(() => {
    // 토큰이 없을시 초기화면으로 이동
    if (hasNotToken(token)) {
      replaceRouterInitialize(router);
    }
  }, []);

  useEffect(() => {
    if (AllertModalstatus !== 0) {
      setTimeout(() => {
        setAllertModalStatus(0); // 2초 후에 AllertModal 닫기
      }, 2000);
    }
  }, [AllertModalstatus]);

  useEffect(() => {
    if (type === 0) {
      axAuth(token)({
        method: 'get',
        url: '/main/ybs',
      })
        .then(res => {
          setUserList(res.data.ybUserInfomationList);
          setIsTodayAttendance(res.data.passAttendanceOfSearchUse);
        })
        .catch(err => {
          console.log(err);
        });
    } else {
      axAuth(token)({
        method: 'get',
        url: '/main/obs',
      })
        .then(res => {
          setUserList(res.data[0].obUserList);
        })
        .catch(err => {
          console.log(err);
        });
    }
    const today = new Date().getDay() - 1;
    if (today < 5 && today) {
    }
  }, [type, isAttendanceModalOpen]);

  return (
    <main>
      {isAttendanceModalOpen ? <AttendanceModal setIsAttendanceModalOpen={setIsAttendanceModalOpen} setAllertModalStatus={setAllertModalStatus} /> : null}
      {AllertModalstatus !== 0 ? (
        <AllertModal title={textOfAllert[AllertModalstatus - 1].title} context={textOfAllert[AllertModalstatus - 1].context} type={textOfAllert[AllertModalstatus - 1].type} />
      ) : null}
      {isMemberInfoOpen !== 0 ? <MemberInformationModal userId={isMemberInfoOpen} setIsMemberInfoOpen={setIsMemberInfoOpen} isKing={isKing} type={type} /> : null}
      <header>
        <Header isVisible={false} />
      </header>
      <section>
        <div className="mx-[7.5%] grid grid-cols-2 mb-[2rem]">
          <div className={`text-center font-bold text-xl' ${type === 0 ? 'text-black' : 'text-grey'}`} onClick={() => setType(0)}>
            현재 인원
          </div>
          <div className={`text-center font-bold text-xl' ${type === 1 ? 'text-black' : 'text-grey'}`} onClick={() => setType(1)}>
            누리 졸업자
          </div>
          <div className={`border-t-2 mt-[1rem] ${type === 0 ? 'border-black' : 'border-grey'}`}></div>
          <div className={`border-t-2 mt-[1rem] ${type === 1 ? 'border-black' : 'border-light-grey'}`}></div>
        </div>

        {isTodayAttendance ? (
          <Button text={'출석완료'} addClass="text-2xl bg-grey" />
        ) : (
          <div onClick={() => setIsAttendanceModalOpen(true)}>
            <Button text={'출석하기'} addClass="text-2xl mb-3" />
          </div>
        )}

        {type === 0 ? (
          <article className="mx-[7.5%]">
            <div className="ml-auto w-[80%] font-bold text-[0.9rem] flex place-content-between">
              <span className="w-[2.2rem] text-center">MON</span>
              <span className="w-[2.2rem] text-center">TUE</span>
              <span className="w-[2.2rem] text-center">WED</span>
              <span className="w-[2.2rem] text-center">THU</span>
              <span className="w-[2.2rem] text-center">FRI</span>
              <span className="w-[3.2rem] text-center">TOKEN</span>
            </div>
            <div className="border border-light-grey mb-[0.5rem]"></div>
            {userList &&
              userList.map((item: userDataPropsTypeZero, idx) => (
                <CurrentMember key={idx} name={item.name} token={item.cntVacation} week={item.weeklyData} userId={item.userId} setIsMemberInfoOpen={setIsMemberInfoOpen} />
              ))}
          </article>
        ) : (
          <article className="mx-[7.5%]">
            <div className={`font-bold text-xl flex mb-[0.5rem] ${isKing ? 'place-content-between' : 'justify-center'}`}>
              <div>이름(등록년도)</div>
              {isKing ? <div>핸드폰 번호</div> : null}
            </div>
            <div className="border border-light-grey mb-[0.5rem]"></div>
            {userList.map((item: userDataPropsTypeOne, idx) => (
              <Graduater key={idx} name={item.name} year={item.yearOfRegistration} phoneNum={item.phoneNum} isKing={isKing} userId={item.userId} setIsMemberInfoOpen={setIsMemberInfoOpen} />
            ))}
          </article>
        )}
      </section>
      <NavigationFooter isKing={isKing}></NavigationFooter>
    </main>
  );
}
function replaceRouterItialize(router: AppRouterInstance) {
  throw new Error('Function not implemented.');
}
