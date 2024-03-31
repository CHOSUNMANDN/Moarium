'use client';
import { axAuth } from '@/apis/axiosinstance';
import Header from '../../atoms/molecule/header';
import { useState, useEffect } from 'react';
import { useRecoilState } from 'recoil';
import { userToken, isNuriKing } from '../../states/index';
import { useRouter } from 'next/navigation';
import Navigation from '../../atoms/template/navigation';
import Button from '../../atoms/atom/large-button';
import AttendanceModal from '../../atoms/molecule/attendance-modal';
import AllertModal from '../../atoms/atom/allert-modal';
import Graduater from '@/atoms/atom/graduater';
import CurrentMember from '@/atoms/molecule/current-member';
import MemberInformationModal from '@/atoms/molecule/member-infromation-modal';
import { hasNotToken } from '@/utils/validate/ExistenceChecker';
import { AppRouterInstance } from 'next/dist/shared/lib/app-router-context';
import { replaceRouterInitialize } from '@/utils/RouteHandling';
import NavigationFooter from '@/atoms/molecule/navigation-footer';
import FavoritesClubInformation from '@/atoms/molecule/favorites-club-information';
import FavoritesClubInformationList from '@/atoms/molecule/favorites-club-information-list';
import StudySearch from '@/atoms/molecule/study-search';

interface ClubFavoriteInformationType {
  clubId: number;
  clubLeaderName: string;
  clubName: string;
  clubProfileImage: string;
  favoriteCheck: string;
  numberMember: string;
}

export default function Main() {
  const router = useRouter();
  const [token, setToken] = useRecoilState(userToken);
  const [clubFavoriteInformations, setClubFavoriteInformations] = useState<ClubFavoriteInformationType[]>([]);
  const [isKing, setIsKing] = useRecoilState(isNuriKing);

  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: '/main',
    })
      .then(res => {
        const clubFindInformations = res.data.result.clubFindInformations;
        setClubFavoriteInformations(clubFindInformations);
      })
      .catch(err => {
        console.log(err);
      });
  }, [clubFavoriteInformations]);

  useEffect(() => {
    // 토큰이 없을시 초기화면으로 이동
    if (hasNotToken(token)) {
      replaceRouterInitialize(router);
    }
  }, []);

  useEffect(() => {
    axAuth(token)({
      method: 'get',
      url: '/main',
    })
      .then(res => {
        const clubFindInformations = res.data.result.clubFindInformations;
        setClubFavoriteInformations(clubFindInformations);
      })
      .catch(err => {
        console.log(err);
      });
  }, [clubFavoriteInformations]);

  return (
    <main>
      <header>
        <Header isVisible={false} />
      </header>
      <section>
        <div>
          <FavoritesClubInformationList data={clubFavoriteInformations} />
        </div>
        <div className="flex flex-col justify-center  items-center ">
          <StudySearch />
        </div>
        <div className="flex justify-center items-center h-[3rem] text-[0.8rem] font-bold c text-[#2db4ec] opacity-70	">
          <div>팀 등록하기</div>
        </div>
      </section>
      <NavigationFooter isKing={isKing}></NavigationFooter>
    </main>
  );
}
function replaceRouterItialize(router: AppRouterInstance) {
  throw new Error('Function not implemented.');
}
