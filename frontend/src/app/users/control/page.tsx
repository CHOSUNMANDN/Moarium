'use client';
import { axAuth } from '@/apis/axiosinstance';
import Header from '../../../atoms/molecule/header';
import { useState, useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { userToken, isNuriKing } from '../../../states/index';
import { useRouter } from 'next/navigation';
import Navigation from '../../../atoms/template/navigation';
import UserListOnControl from '@/atoms/molecule/user-list-control';
import { hasNotToken, isNotNuriKing } from '@/utils/validate/ExistenceChecker';
import { replaceRouterInitialize, replaceRouterMain } from '@/utils/RouteHandling';
import NavigationFooter from '@/atoms/molecule/navigation-footer';
import { RouteUrl } from '@/utils/constans/routeEnum';

interface userDataPropsTypeOne {
  name: string;
  userId: number;
}

export default function Main() {
  const router = useRouter();
  const [token, setToken] = useRecoilState(userToken);
  const [type, setType] = useState(0);
  const [userList, setUserList] = useState([]);
  const [isKing, setIsKing] = useRecoilState(isNuriKing);
  const [isAttendanceModalOpen, setIsAttendanceModalOpen] = useState(false);
  const [isTodayAttendance, setIsTodayAttendance] = useState(false);
  const [isMemberInfoOpen, setIsMemberInfoOpen] = useState(0);
  const [page, setPage] = useState(1);
  const kingManagePageCss = 'flex justify-around items-center w-[25vw] bg-[#ffffff] rounded-lg h-[2.5rem] font-bold';

  useEffect(() => {
    // 토큰이 없을시 초기화면으로 이동
    if (hasNotToken(token)) {
      replaceRouterInitialize(router);
    }

    // 실장이 아니면 메인페이지으로 이동
    if (isNotNuriKing(isKing)) {
      replaceRouterMain(router);
    }
  }, []);

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
    }
  }, [type, isAttendanceModalOpen]);

  return (
    <main>
      <header>
        <Header isVisible={false} />
      </header>
      <section>
        <div className="mx-[7.5%] grid grid-cols-2 mb-[1rem]">
          <div className={`text-center font-bold text-xl' ${type === 0 ? 'text-black' : 'text-grey'}`} onClick={() => setType(0)}>
            현재 인원 개인별 설정
          </div>
        </div>
        <article className="mx-[7.5%]">
          {userList.map((item: userDataPropsTypeOne, index) => (
            <UserListOnControl key={index} name={item.name} isKing={isKing} userId={item.userId} setIsMemberInfoOpen={setIsMemberInfoOpen} />
          ))}
        </article>
      </section>

      <div className="w-[100vw] flex justify-around items-center bg-[#F3F3F3] h-[3rem]  bottom-[3.1rem] fixed">
        <div className={kingManagePageCss} onClick={() => router.push(RouteUrl.ROUTE_JOIN_APPLICATION_LIST)}>
          신입 신청 확인
        </div>
        <div className={kingManagePageCss} onClick={() => router.push(RouteUrl.ROUTE_ATTENDANCE_NUMBER)}>
          출석 번호
        </div>
      </div>
      <NavigationFooter isKing={isKing}></NavigationFooter>
    </main>
  );
}
