'use client';
import { axAuth } from '@/apis/axiosinstance';
import SmallHeader from '../../../atoms/molecule/SmallHeader';
import { useState, useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { userToken, myClubGrade, myClubMemberId } from '../../../states/index';
import Graduater from '@/atoms/atom/graduater';
import CurrentMember from '@/atoms/molecule/CurrentMember';
import { useRouter } from 'next/navigation';
import LongThickButton from '../../../atoms/atom/LongThickButton';
import AttendanceModal from '../../../atoms/molecule/AttendanceModal';
import MemberInformationModal from '@/atoms/molecule/MemberInformationModal';
import { AppRouterInstance } from 'next/dist/shared/lib/app-router-context';
import NavigationFooter from '@/atoms/molecule/navigation-footer';
import { usePathname } from 'next/navigation';

interface userDataPropsTypeZero {
  clubMemberId: number;
  memberName: string;
  attendanceStatus: { [key: string]: string };
  vacationToken: string;
  isAttendanceCheckDate: { [key: string]: string } | undefined;
}

export default function Club() {
  const router = useRouter();
  const [token, setToken] = useRecoilState(userToken);
  const [type, setType] = useState(0);
  const [userList, setUserList] = useState<userDataPropsTypeZero[]>([]);
  const [dormantUserList, setDormantUserList] = useState([]);
  const [isMyClubGrade, setIsMyClubGrade] = useRecoilState(myClubGrade);
  const [isAttendanceModalOpen, setIsAttendanceModalOpen] = useState(false);
  const [allertModalstatus, setAllertModalStatus] = useState(0);
  const [isTodayAttendance, setIsTodayAttendance] = useState(false);
  const [isMemberInfoOpen, setIsMemberInfoOpen] = useState(0);
  const [isMemberToken, setIsMemberToken] = useState('');
  const [isAttendanceCheckDate, setIsAttendanceCheckDate] = useState<{ [key: string]: string } | undefined>(undefined);
  const [isAttendancePossibleDate, setIsAttendancePossibleDate] = useState(false);
  const [isMyClubMemberId, setIsMyClubMemberId] = useRecoilState(myClubMemberId);
  const [isClubName, setIsClubName] = useState('');
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
        url: `/clubs/informations/${id}/details`,
      })
        .then(res => {
          setUserList(res.data.result.clubMembers);
          setIsClubName(res.data.result.clubName);
          setIsMyClubGrade(res.data.result.myClubGrade);
          setIsMyClubMemberId(res.data.result.myClubMemberId);
          setIsAttendanceCheckDate(res.data.result.clubMemberAttendanceCheckDate);
        })
        .catch(err => {
          console.log(err);
        });
    } else {
      axAuth(token)({
        method: 'get',
        url: `/clubs/informations/${id}/details/dormancys`,
      })
        .then(res => {
          setDormantUserList(res.data.result.dormancyMembers);
        })
        .catch(err => {
          console.log(err);
        });
    }
  }, [type, isAttendanceModalOpen, isMemberInfoOpen]);

  // 오늘 요일을 찾아서 출석이 되었는 지 확인한다.
  useEffect(() => {
    const days = ['sunday', 'monday', 'tuesday', 'wednesday', 'thursday', 'friday', 'saturday'];
    const today = new Date().getDay();
    const todayDay = days[today];
    const todayDayString = days[today] + 'Check';
    if (userList && todayDay) {
      const member = userList.find(member => member.clubMemberId === isMyClubMemberId);
      if (member) {
        const todayStatus = member.attendanceStatus[todayDay];
        if (todayStatus === 'ATTENDANCE' || todayStatus === 'VACATION') {
          setIsTodayAttendance(true);
        }
      }
    }
    // 오늘 출석이 가능한 지 확인한다. 만약 불가능하다면 출석완료 표시를 한다.
    if (isAttendanceCheckDate && todayDayString) {
      setIsAttendancePossibleDate(isAttendanceCheckDate[todayDayString] === 'POSSIBLE');
    }
  });

  // 모달창이 열렸을 때 스크롤을 비활성화 하도록 한다.
  useEffect(() => {
    if (isAttendanceModalOpen || isMemberInfoOpen !== 0) {
      const originalStyle = window.getComputedStyle(document.body).overflow;
      document.body.style.overflow = 'hidden';
      return () => {
        document.body.style.overflow = originalStyle;
      };
    }
  }, [isAttendanceModalOpen, isMemberInfoOpen]);

  return (
    <main>
      {isAttendanceModalOpen ? <AttendanceModal clubId={id} clubMemberId={isMyClubMemberId} setIsAttendanceModalOpen={setIsAttendanceModalOpen} setAllertModalStatus={setAllertModalStatus} /> : null}
      {isMemberInfoOpen !== 0 ? (
        <MemberInformationModal clubId={id} clubMemberId={isMemberInfoOpen} vacationToken={isMemberToken} setIsMemberInfoOpen={setIsMemberInfoOpen} isMyClubGrade={isMyClubGrade} type={type} />
      ) : null}
      <header>
        <SmallHeader clubName={isClubName} />
      </header>
      <section>
        <div className="mx-[7.5%] grid grid-cols-2 mb-[2rem]">
          <div className={`text-center font-bold text-[1.5rem] ${type === 0 ? 'text-black' : 'text-grey'}`} onClick={() => setType(0)}>
            현재 인원
          </div>
          <div className={`text-center font-bold text-[1.5rem] ${type === 1 ? 'text-black' : 'text-grey'}`} onClick={() => setType(1)}>
            휴면 인원
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
                <CurrentMember
                  key={idx}
                  name={item.memberName}
                  token={item.vacationToken}
                  week={item.attendanceStatus}
                  userId={item.clubMemberId}
                  setIsMemberInfoOpen={setIsMemberInfoOpen}
                  setIsMemberToken={setIsMemberToken}
                  isAttendanceCheckDate={isAttendanceCheckDate}
                />
              ))}
          </article>
        ) : (
          <article className="mx-[7.5%]">
            <div className="flex h-[1.5rem]"></div>
            <div className="border border-light-grey mb-[0.5rem]"></div>
            {dormantUserList.map((memberName: string, idx) => (
              <Graduater key={idx} name={memberName} />
            ))}
          </article>
        )}
      </section>
      {type === 0 ? (
        <div className="fixed inset-x-[0rem] bottom-[4rem] mx-auto w-max">
          {isTodayAttendance || !isAttendancePossibleDate ? (
            <LongThickButton text={'출석완료'} addClass="text-2xl bg-grey" />
          ) : (
            <div
              onClick={() => {
                setIsAttendanceModalOpen(true);
                window.scrollTo(0, 0);
              }}
            >
              <LongThickButton text={'출석하기'} addClass="text-2xl" />
            </div>
          )}
        </div>
      ) : (
        <div></div>
      )}
      <NavigationFooter isMyClubGrade={isMyClubGrade}></NavigationFooter>
    </main>
  );
}
function replaceRouterItialize(router: AppRouterInstance) {
  throw new Error('Function not implemented.');
}
