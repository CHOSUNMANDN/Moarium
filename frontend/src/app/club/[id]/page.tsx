'use client';
import { axAuth } from '@/apis/axiosinstance';
import Header from '../../../atoms/molecule/header';
import { useState, useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { userToken, isNuriKing } from '../../../states/index';
import Graduater from '@/atoms/atom/graduater';
import CurrentMember from '@/atoms/molecule/current-member';
import { useRouter } from 'next/navigation';
import Button from '../../../atoms/atom/large-button';
import AttendanceModal from '../../../atoms/molecule/attendance-modal';
import { AppRouterInstance } from 'next/dist/shared/lib/app-router-context';
import NavigationFooter from '@/atoms/molecule/navigation-footer';
import { usePathname } from 'next/navigation';

interface userDataPropsTypeZero {
  clubMemberId: number;
  memberName: string;
  attendanceStatus: string[];
  vacationToken: string;
}

interface userDataPropsTypeOne {
  memberName: string;
}

export default function Club() {
  const router = useRouter();
  const [token, setToken] = useRecoilState(userToken);
  const [type, setType] = useState(0);
  const [userList, setUserList] = useState([]);
  const [isKing, setIsKing] = useRecoilState(isNuriKing);
  const [isAttendanceModalOpen, setIsAttendanceModalOpen] = useState(false);
  const [AllertModalstatus, setAllertModalStatus] = useState(0);
  const [isTodayAttendance, setIsTodayAttendance] = useState(false);
  const [isMemberInfoOpen, setIsMemberInfoOpen] = useState(0);
  const pathName: string = usePathname();
  let id: string | undefined;

  if (typeof pathName === 'string') {
    id = pathName.split('/').pop();
  } else {
    // pathName이 undefined인 경우에 대한 처리
    id = undefined;
  }

  useEffect(() => {
    if (type === 0) {
      axAuth(token)({
        method: 'get',
        url: '/clubs/informations/' + id + '/details',
      })
        .then(res => {
          setUserList(res.data.result.clubMembers);
        })
        .catch(err => {
          console.log(err);
        });
    } else {
      axAuth(token)({
        method: 'get',
        url: '/clubs/informations/' + id + '/details/dormancys',
      })
        .then(res => {
          setUserList(res.data.result.dormancys);
        })
        .catch(err => {
          console.log(err);
        });
    }
    const today = new Date().getDay() - 1;
    if (today < 7 && today) {
    }
  }, [type, isAttendanceModalOpen]);

  return (
    <main>
      {isAttendanceModalOpen ? <AttendanceModal setIsAttendanceModalOpen={setIsAttendanceModalOpen} setAllertModalStatus={setAllertModalStatus} /> : null}
      <header>
        <Header isVisible={false} />
      </header>
      <section>
        <div className="mx-[7.5%] grid grid-cols-2 mb-[2rem]">
          <div className={`text-center font-bold text-[1.5rem] ${type === 0 ? 'text-black' : 'text-grey'}`} onClick={() => setType(0)}>
            현재 인원
          </div>
          <div className={`text-center font-bold text-[1.5rem] ${type === 1 ? 'text-black' : 'text-grey'}`} onClick={() => setType(1)}>
            휴먼 인원
          </div>
          <div className={`border-t-2 mt-[1rem] ${type === 0 ? 'border-black' : 'border-grey'}`}></div>
          <div className={`border-t-2 mt-[1rem] ${type === 1 ? 'border-black' : 'border-light-grey'}`}></div>
        </div>

        {type === 0 ? (
          <article className="mx-[7.5%]">
            <div className="h-[1.5rem] ml-auto w-[100%] font-bold text-[0.9rem] flex place-content-between">
              <span className="w-[2.2rem] text-center">MON</span>
              <span className="w-[2.2rem] text-center">TUE</span>
              <span className="w-[2.2rem] text-center">WED</span>
              <span className="w-[2.2rem] text-center">THU</span>
              <span className="w-[2.2rem] text-center">FRI</span>
              <span className="w-[2.2rem] text-center">SAT</span>
              <span className="w-[2.2rem] text-center">SUN</span>
              <span className="w-[3.2rem] text-center">TOKEN</span>
            </div>
            <div className="border border-light-grey mb-[0.5rem]"></div>
            {userList &&
              userList.map((item: userDataPropsTypeZero, idx) => (
                <CurrentMember key={idx} name={item.memberName} token={item.vacationToken} week={item.attendanceStatus} userId={item.clubMemberId} setIsMemberInfoOpen={setIsMemberInfoOpen} />
              ))}
          </article>
        ) : (
          <article className="mx-[7.5%]">
            <div className="flex h-[1.5rem]"></div>
            <div className="border border-light-grey mb-[0.5rem]"></div>
            {userList.map((item: userDataPropsTypeOne, idx) => (
              <Graduater key={idx} name={item.memberName} />
            ))}
          </article>
        )}
      </section>
      {type === 0 ? (
        <div className="fixed inset-x-[3rem] bottom-[3rem]">
          {isTodayAttendance ? (
            <Button text={'출석완료'} addClass="text-2xl bg-grey" />
          ) : (
            <div onClick={() => setIsAttendanceModalOpen(true)}>
              <Button text={'출석하기'} addClass="text-2xl mb-3" />
            </div>
          )}
        </div>
      ) : (
        <div></div>
      )}
      <NavigationFooter isKing={isKing}></NavigationFooter>
    </main>
  );
}
function replaceRouterItialize(router: AppRouterInstance) {
  throw new Error('Function not implemented.');
}
